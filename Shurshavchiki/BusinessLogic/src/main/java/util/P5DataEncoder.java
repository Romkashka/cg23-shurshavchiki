package util;

public class P5DataEncoder implements PnmImageDataEncoder {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public char nextChar() {
        return 0;
    }

    @Override
    public char[] createCharBuffer() {
        return new char[0];
    }
}
