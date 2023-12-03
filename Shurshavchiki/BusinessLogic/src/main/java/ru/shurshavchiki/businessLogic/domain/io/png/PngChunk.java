package ru.shurshavchiki.businessLogic.domain.io.png;

import java.util.Arrays;

public record PngChunk(String ChunkType, int Length, byte[] Data, int Crc) {
    @Override
    public String toString() {
        return "PngChunk{" +
                "ChunkType='" + ChunkType + '\'' +
                ", Length=" + Length +
                ", Crc=" + Crc +
                '}';
    }
}
