package ru.shurshavchiki.businessLogic.domain.io.png;

import ru.shurshavchiki.businessLogic.domain.io.png.chunkDataExtractorImplementations.EmptyPngChunkDataConverter;
import ru.shurshavchiki.businessLogic.domain.io.png.chunkDataExtractorImplementations.PngChunkDataConverterBase;

public class ChunkDataExtractorChainInitializer {
    public PngChunkDataConverter initializeChain() {
        return new PngChunkDataConverterBase();
    }
}
