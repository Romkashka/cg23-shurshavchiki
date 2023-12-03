package ru.shurshavchiki.businessLogic.domain.io;

import ru.shurshavchiki.businessLogic.domain.io.png.ChunkDataExtractorChainInitializer;
import ru.shurshavchiki.businessLogic.domain.io.png.PngFileReader;
import ru.shurshavchiki.businessLogic.domain.io.pnm.PnmFileReader;
import ru.shurshavchiki.businessLogic.exceptions.OpenFileException;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FileFormatSpecifier {
    public static final int SAMPLE_LENGTH = 12;
    private static final ChunkDataExtractorChainInitializer initializer = new ChunkDataExtractorChainInitializer();
    public static final Map<byte[], FileReader> SUPPORTED_FILE_READERS = new HashMap<>() {{
        put(new byte[] {-119, 80, 78, 71, 13, 10, 26, 10}, new PngFileReader(initializer.initializeChain()));
        put("P6".getBytes(), new PnmFileReader());
        put("P5".getBytes(), new PnmFileReader());
    }};

    public FileReader defineFileReader(File file) throws IOException {
        DataInputStream stream = new DataInputStream(new FileInputStream(file));

        byte[] sample = stream.readNBytes(SAMPLE_LENGTH);

        for (var instance: SUPPORTED_FILE_READERS.keySet()) {
            if (Arrays.equals(instance, 0, instance.length, sample, 0, instance.length)) {
                stream.close();
                return SUPPORTED_FILE_READERS.get(instance);
            }
        }

        stream.close();
        throw OpenFileException.unsupportedFileFormat();
    }
}
