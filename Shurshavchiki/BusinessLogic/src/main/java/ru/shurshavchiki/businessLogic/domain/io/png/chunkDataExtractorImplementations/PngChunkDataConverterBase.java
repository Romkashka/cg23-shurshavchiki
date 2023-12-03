package ru.shurshavchiki.businessLogic.domain.io.png.chunkDataExtractorImplementations;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.businessLogic.domain.io.png.PngChunkDataConverter;
import ru.shurshavchiki.businessLogic.domain.io.png.PngChunk;
import ru.shurshavchiki.businessLogic.domain.models.ImageDataHolder;

import java.util.List;
import java.util.stream.Stream;

public abstract class PngChunkDataConverterBase implements PngChunkDataConverter {
    @Setter
    private PngChunkDataConverter nextDataExtractor;
    @Getter
    private String chunkType;

    @Override
    public void extractData(PngChunk chunk, ImageDataHolder dataHolder) {
        if (chunk.ChunkType().equals(chunkType)) {
            handleChunk(chunk, dataHolder);
            return;
        }
        if (nextDataExtractor != null) {
            nextDataExtractor.extractData(chunk, dataHolder);
        }
    }

    @Override
    public List<PngChunk> storeData(List<PngChunk> storedChunks, ImageDataHolder dataHolder) {
        List<PngChunk> currentStageChinks = convertRawData(dataHolder);
        return nextDataExtractor.storeData(Stream.concat(storedChunks.stream(), currentStageChinks.stream()).toList(), dataHolder);
    }

    protected abstract void handleChunk(PngChunk chunk, ImageDataHolder dataHolder);

    protected abstract List<PngChunk> convertRawData(ImageDataHolder dataHolder);
}
