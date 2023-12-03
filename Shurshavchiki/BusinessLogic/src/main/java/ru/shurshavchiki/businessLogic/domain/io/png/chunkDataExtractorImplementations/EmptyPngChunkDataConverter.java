package ru.shurshavchiki.businessLogic.domain.io.png.chunkDataExtractorImplementations;

import ru.shurshavchiki.businessLogic.domain.io.png.PngChunk;
import ru.shurshavchiki.businessLogic.domain.models.ImageDataHolder;

import java.util.LinkedList;
import java.util.List;

public class EmptyPngChunkDataConverter extends PngChunkDataConverterBase {
    @Override
    protected void handleChunk(PngChunk chunk, ImageDataHolder dataHolder) {
        System.out.println("Empty handling");
    }

    @Override
    protected List<PngChunk> convertRawData(ImageDataHolder dataHolder) {
        System.out.println("Empty converting");
        return new LinkedList<>();
    }
}
