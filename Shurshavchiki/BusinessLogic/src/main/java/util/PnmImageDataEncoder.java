package util;

public interface PnmImageDataEncoder {
    boolean hasNext();
    byte[] createCharBuffer();
}
