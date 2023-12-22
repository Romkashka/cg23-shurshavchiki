package ru.shurshavchiki.businessLogic.domain.io.png;

import ru.shurshavchiki.businessLogic.domain.models.ImageDataHolder;

import java.util.List;
import java.util.zip.DataFormatException;

public interface PngChunkDataConverter {
    void extractData(PngChunk chunk, ImageDataHolder dataHolder);
    void processIDATChunks(ImageDataHolder dataHolder) throws DataFormatException;

    List<PngChunk> storeData(List<PngChunk> storedChunks, ImageDataHolder dataHolder);
}
