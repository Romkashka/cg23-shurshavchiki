package ru.shurshavchiki.businessLogic.domain.io.png;

import ru.shurshavchiki.businessLogic.domain.io.FileWriter;
import ru.shurshavchiki.businessLogic.domain.models.ImageDataHolder;
import ru.shurshavchiki.businessLogic.exceptions.WriteFileException;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PngFileWriter implements FileWriter {
    private static final byte[] VALID_HEADER = new byte[] {-119, 80, 78, 71, 13, 10, 26, 10};
    private static final byte[] IEND_CHUNK = new byte[] {0, 0, 0, 0, 73, 69, 78, 68, -82, 66, 96, -126};
    private final PngChunkDataConverter pngChunkDataConverter;

    public PngFileWriter(PngChunkDataConverter pngChunkDataConverter) {
        this.pngChunkDataConverter = pngChunkDataConverter;
    }

    @Override
    public void saveFromRawData(File file, ImageDataHolder imageDataHolder) throws IOException {
        List<PngChunk> allChunks = pngChunkDataConverter.storeData(new ArrayList<>(), imageDataHolder);

        DataOutputStream stream = new DataOutputStream(new FileOutputStream(file));

        stream.write(VALID_HEADER);

        Optional<PngChunk> ihdr = allChunks.stream().filter(chunk -> chunk.ChunkType().equals("IHDR")).findAny();
        if (ihdr.isEmpty()) {
            throw WriteFileException.criticalChunkIsAbsent("IHDR");
        }

        stream.write(convertChunk(ihdr.get()));
        allChunks.remove(ihdr);

        for (PngChunk chunk: allChunks) {
            stream.write(convertChunk(chunk));
        }

        stream.write(IEND_CHUNK);
    }

    private byte[] convertChunk(PngChunk chunk) {
        byte[] result = new byte[chunk.Length() + 12];

        System.arraycopy(BigInteger.valueOf(chunk.Length()).toByteArray(), 0, result, 0, 4);
        System.arraycopy(chunk.ChunkType().getBytes(), 0, result, 4, 4);
        System.arraycopy(chunk.Data(), 0, result, 8, chunk.Length());

        int crc = calculateCrc(Arrays.copyOfRange(result, 4, result.length));
        System.arraycopy(BigInteger.valueOf(crc).toByteArray(), 0, result, 8 + chunk.Length(), 4);

        return result;
    }

    private int calculateCrc(byte[] data) {
        throw new UnsupportedOperationException("Implement CRC, please");
    }
}
