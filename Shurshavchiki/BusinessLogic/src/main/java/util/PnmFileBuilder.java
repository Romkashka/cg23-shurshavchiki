package util;

import models.RgbConvertable;

import java.util.ArrayList;

public class PnmFileBuilder {
    private String version;
    private int height;
    private int width;
    private int maxval;

    private ArrayList<ArrayList<RgbConvertable>> pixels;
    private PixelDataReader dataReader; // selected based on file version
}
