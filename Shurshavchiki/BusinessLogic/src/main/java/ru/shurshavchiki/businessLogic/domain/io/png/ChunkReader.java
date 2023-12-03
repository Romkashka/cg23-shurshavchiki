package ru.shurshavchiki.businessLogic.domain.io.png;

import java.io.DataInputStream;
import java.io.IOException;
import java.math.BigInteger;

public class ChunkReader {
    public PngChunk readChunk(DataInputStream stream) throws IOException {
        int length = readNextInt(stream);
        String chunkType = readNextString(stream);
        byte[] data = stream.readNBytes(length);
        int crc = readNextInt(stream);
        PngChunk chunk = new PngChunk(chunkType, length, data, crc);
        System.out.println(chunk);
        return chunk;
    }

    private int readNextInt(DataInputStream stream) throws IOException {
        return new BigInteger(readNextWord(stream)).intValue();
    }

    private String readNextString(DataInputStream stream) throws IOException {
        return new String(readNextWord(stream));
    }

    private byte[] readNextWord(DataInputStream stream) throws IOException {
        return stream.readNBytes(4);
    }
}
