package ru.shurshavchiki.businessLogic.domain.io.png;

import ru.shurshavchiki.businessLogic.domain.io.FileReader;
import ru.shurshavchiki.businessLogic.domain.models.ImageDataHolder;
import ru.shurshavchiki.businessLogic.exceptions.OpenFileException;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

public class PngFileReader implements FileReader {
    private static final byte[] VALID_HEADER = new byte[] {-119, 80, 78, 71, 13, 10, 26, 10};
    private DataInputStream stream;
    private final PngChunkDataConverter pngChunkDataConverter;

    public PngFileReader(PngChunkDataConverter pngChunkDataConverter) {
        this.pngChunkDataConverter = pngChunkDataConverter;
    }

    public ImageDataHolder getImageDataHolder(File file) throws IOException {
        stream = new DataInputStream(new FileInputStream(file));
        validateHeader();

        boolean isFinished = false;
        ChunkReader chunkReader = new ChunkReader();
        ImageDataHolder imageDataHolder = new ImageDataHolder();
        while (!isFinished) {
            PngChunk chunk = chunkReader.readChunk(stream);
            isFinished = chunk.ChunkType().equals("IEND");
            pngChunkDataConverter.extractData(chunk, imageDataHolder);
        }

        return imageDataHolder;
    }

    private void validateHeader() throws IOException {
        byte[] header = stream.readNBytes(8);

        if (!Arrays.equals(header, VALID_HEADER)) {
            throw OpenFileException.invalidPngHeader();
        }
    }
}
