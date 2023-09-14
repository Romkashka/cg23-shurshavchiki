package util;

import lombok.NonNull;
import models.RgbConvertable;

import java.util.Scanner;

public class P5DataReader implements PixelDataReader {
    private Scanner scanner;


    @Override
    public @NonNull RgbConvertable nextPixel() {
        // returns MonochromePixel
        return null;
    }

    @Override
    public boolean hasNext() {
        return false;
    }
}
