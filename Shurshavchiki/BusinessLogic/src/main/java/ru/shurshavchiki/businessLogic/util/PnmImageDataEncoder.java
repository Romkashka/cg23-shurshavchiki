package ru.shurshavchiki.businessLogic.util;

public interface PnmImageDataEncoder {
    boolean hasNext();
    byte[] createCharBuffer();
}
