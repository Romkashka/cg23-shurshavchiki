package ru.shurshavchiki.businessLogic.domain.io.pnm;

public interface PnmImageDataEncoder {
    boolean hasNext();
    byte[] createCharBuffer();
}
