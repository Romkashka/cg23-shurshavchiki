package util;

public class P6DataEncoder implements PnmImageDataEncoder {
    @Override
    public boolean hasNext() {
        return false;
    }

    @Override
    public char nextChar() {
        return 0;
    }
}
