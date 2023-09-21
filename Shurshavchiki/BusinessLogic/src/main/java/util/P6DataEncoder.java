package util;

import entities.PnmDisplayable;
import models.RgbConvertable;

public class P6DataEncoder implements PnmImageDataEncoder {
    public static final int MIN_BITS_PER_COLOR = 8;
    public static final int MIN_DATA_SIZE = (int) Math.pow(2, MIN_BITS_PER_COLOR) - 1;
    private PnmDisplayable pnmFile;
    private int x;
    private int y;
    private final int colorDataSize;

    public P6DataEncoder(PnmDisplayable pnmFile) {
        this.pnmFile = pnmFile;
        x = 0;
        y = 0;
        colorDataSize = calculateColorDataSize();
        System.out.println("Color data size: " + colorDataSize);
    }

    @Override
    public boolean hasNext() {
        return y < pnmFile.getHeight();
    }

    @Override
    public char nextChar() {
        return 0;
    }

    @Override
    public char[] createCharBuffer() {
        char[] data = new char[pnmFile.getHeight() * pnmFile.getWidth() * colorDataSize * 3];
        int offset = 0;
        while (hasNext()) {
            offset = convertPixel(getCurrentPixel(), offset, data);
        }

        return data;
    }

    public int convertPixel(RgbConvertable pixel, int offset, char[] data) {
        offset = writeColor(pixel.Red(), offset, data);
        offset = writeColor(pixel.Green(), offset, data);
        return writeColor(pixel.Blue(), offset, data);
    }

    private int writeColor(int colorValue, int offset, char[] data) {
        for (int i = 0; i < colorDataSize; i++) {
            data[offset] = (char) (colorValue % MIN_DATA_SIZE);
            offset++;
            colorValue = colorValue >> MIN_BITS_PER_COLOR;
        }

        return offset;
    }

    private RgbConvertable getCurrentPixel() {
        RgbConvertable result = pnmFile.getPixel(x, y);
        moveToNextPixel();
        return result;
    }

    private void moveToNextPixel() {
        x++;
        if (x == pnmFile.getWidth()) {
            y++;
            x %= pnmFile.getWidth();
        }
    }

    private int calculateColorDataSize() {
        if (pnmFile.getMaxval() <= MIN_DATA_SIZE) {
            return 1;
        }
        else {
            return 2;
        }
    }
}
