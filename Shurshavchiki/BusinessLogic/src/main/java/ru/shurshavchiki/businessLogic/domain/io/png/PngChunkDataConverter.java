package ru.shurshavchiki.businessLogic.domain.io.png;

import ru.shurshavchiki.businessLogic.domain.models.ImageDataHolder;

import java.util.List;

public interface PngChunkDataConverter {
    void setNextDataExtractor(PngChunkDataConverter nextPngChunkDataConverter);
    void extractData(PngChunk chunk, ImageDataHolder dataHolder);

    List<PngChunk> storeData(List<PngChunk> storedChunks, ImageDataHolder dataHolder);
}
