package util;

import lombok.NonNull;
import models.RgbConvertable;

public interface PixelDataReader {
    @NonNull RgbConvertable nextPixel();
    boolean hasNext();
}
