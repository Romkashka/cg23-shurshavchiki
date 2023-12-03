package ru.shurshavchiki.businessLogic.domain.io.png;

import ru.shurshavchiki.businessLogic.domain.io.png.chunkDataExtractorImplementations.EmptyPngChunkDataConverter;

public class ChunkDataExtractorChainInitializer {
    public PngChunkDataConverter initializeChain() {
        return new EmptyPngChunkDataConverter();
    }
}
