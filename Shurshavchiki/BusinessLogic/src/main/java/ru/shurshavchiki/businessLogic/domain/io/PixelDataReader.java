package ru.shurshavchiki.businessLogic.domain.io;

import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;

import java.io.IOException;
import java.util.ArrayList;

public interface PixelDataReader {
    ArrayList<RgbConvertable> nextPixel() throws IOException;
    float[] getFloatPixels() throws IOException;
    boolean hasNext();
}
