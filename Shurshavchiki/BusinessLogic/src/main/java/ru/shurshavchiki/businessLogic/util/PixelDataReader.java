package ru.shurshavchiki.businessLogic.util;

import ru.shurshavchiki.businessLogic.models.RgbConvertable;

import java.io.IOException;
import java.util.ArrayList;

public interface PixelDataReader {
    ArrayList<RgbConvertable> nextPixel() throws IOException;
    boolean hasNext();
}
