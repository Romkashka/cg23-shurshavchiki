package util;

import lombok.NonNull;
import models.RgbConvertable;

import java.io.IOException;

public interface PixelDataReader {
    @NonNull RgbConvertable nextPixel() throws IOException;
    boolean hasNext();
}
