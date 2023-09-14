package util;

import lombok.NonNull;
import models.RgbConvertable;

public class P6DataReader implements PixelDataReader {
    @Override
    public @NonNull RgbConvertable nextPixel() {
        // returns RgbPixel
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }
}
