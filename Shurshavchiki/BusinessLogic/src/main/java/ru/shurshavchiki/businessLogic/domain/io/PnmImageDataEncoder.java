package ru.shurshavchiki.businessLogic.domain.io;

public interface PnmImageDataEncoder {
    boolean hasNext();
    byte[] createCharBuffer();
}
