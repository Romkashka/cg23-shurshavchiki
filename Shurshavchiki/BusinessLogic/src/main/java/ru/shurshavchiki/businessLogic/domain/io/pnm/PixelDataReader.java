package ru.shurshavchiki.businessLogic.domain.io.pnm;

import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;

import java.io.IOException;
import java.util.ArrayList;

public interface PixelDataReader {
    ArrayList<RgbConvertable> nextPixel() throws IOException;
    byte[] getAllPixels() throws IOException;
    boolean hasNext();
}
