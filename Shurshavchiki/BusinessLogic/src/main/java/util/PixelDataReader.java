package util;

import lombok.NonNull;
import models.RgbConvertable;

import java.io.IOException;
import java.util.ArrayList;

public interface PixelDataReader {
    ArrayList<RgbConvertable> nextPixel() throws IOException;
    boolean hasNext();
}
