package ru.shurshavchiki.businessLogic.domain.io;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;

public abstract class AbstractDataEncoder implements PnmImageDataEncoder {
    protected static final int MIN_BITS_PER_COLOR = 8;
    protected static final int MIN_DATA_SIZE = (int) Math.pow(2, MIN_BITS_PER_COLOR) - 1;
    protected final Displayable displayable;
    protected int x;
    protected int y;
    protected final int colorDataSize;
    protected final int channelsCount;

    protected AbstractDataEncoder(Displayable displayable, int channelsCount) {
        this.displayable = displayable;
        this.channelsCount = channelsCount;
        x = 0;
        y = 0;
        colorDataSize = calculateColorDataSize();
    }

    @Override
    public boolean hasNext() {
        return y < displayable.getHeight();
    }

    @Override
    public byte[] createCharBuffer() {
        byte[] data = new byte[displayable.getHeight() * displayable.getWidth() * colorDataSize * channelsCount];
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
            offset++;
        }

        return offset;
    }

    protected RgbConvertable getCurrentPixel() {
        RgbConvertable result = displayable.getPixel(x, y);
        moveToNextPixel();
        return result;
    }

    protected void moveToNextPixel() {
        x++;
        if (x == displayable.getWidth()) {
            y++;
            x %= displayable.getWidth();
        }
    }

    protected int calculateColorDataSize() {
        if (255 <= MIN_DATA_SIZE) {
            return 1;
        }
        else {
            return 2;
        }
    }
}
