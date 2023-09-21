package util;

import entities.PnmDisplayable;
import models.RgbConvertable;

public abstract class AbstractDataEncoder implements PnmImageDataEncoder {
    protected static final int MIN_BITS_PER_COLOR = 8;
    protected static final int MIN_DATA_SIZE = (int) Math.pow(2, MIN_BITS_PER_COLOR) - 1;
    protected PnmDisplayable pnmFile;
    protected int x;
    protected int y;
    protected final int colorDataSize;

    protected AbstractDataEncoder(PnmDisplayable pnmFile) {
        this.pnmFile = pnmFile;
        x = 0;
        y = 0;
        colorDataSize = calculateColorDataSize();
    }

    @Override
    public boolean hasNext() {
        return y < pnmFile.getHeight();
    }

    @Override
    public byte[] createCharBuffer() {
        byte[] data = new byte[pnmFile.getHeight() * pnmFile.getWidth() * colorDataSize * 3];
        int offset = 0;
        while (hasNext()) {
            offset = convertPixel(getCurrentPixel(), offset, data);
        }

        return data;
    }

    protected abstract int convertPixel(RgbConvertable pixel, int offset, byte[] data);

    protected int writeColor(int colorValue, int offset, byte[] data) {
        for (int i = colorDataSize-1; i >= 0; i--) {
            data[offset] = (byte) (colorValue & (MIN_DATA_SIZE << MIN_BITS_PER_COLOR * i));
            if (x == 1 && y == 0) {
                System.out.println((colorValue & (MIN_DATA_SIZE << MIN_BITS_PER_COLOR * i)) + " " + (int) data[offset]);
            }
            offset++;
        }

        return offset;
    }

    protected RgbConvertable getCurrentPixel() {
        RgbConvertable result = pnmFile.getPixel(x, y);
        moveToNextPixel();
        return result;
    }

    protected void moveToNextPixel() {
        x++;
        if (x == pnmFile.getWidth()) {
            y++;
            x %= pnmFile.getWidth();
        }
    }

    protected int calculateColorDataSize() {
        if (pnmFile.getMaxval() <= MIN_DATA_SIZE) {
            return 1;
        }
        else {
            return 2;
        }
    }
}
