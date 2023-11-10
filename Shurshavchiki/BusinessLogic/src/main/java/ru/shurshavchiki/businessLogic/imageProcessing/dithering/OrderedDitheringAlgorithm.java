package ru.shurshavchiki.businessLogic.imageProcessing.dithering;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class OrderedDitheringAlgorithm extends DitheringAlgorithmBase{

    private float[][] M = {
            {0, 32, 8, 40, 2, 34, 10, 42},
            {48, 16, 56, 24, 50, 18, 58, 26},
            {12, 44, 4, 36, 14, 46, 6, 38},
            {60, 28, 52, 20, 62, 30, 54, 22},
            {3, 35, 11, 43, 1, 33, 9, 41},
            {51, 19, 59, 27, 49, 17, 57, 25},
            {15, 47, 7, 39, 13, 45, 5, 37},
            {63, 31, 55, 23, 61, 29, 53, 21}
    };

    @Override
    public String getName() {
        return "Ordered";
    }

    @Override
    public Displayable applyDithering(Displayable inputImage) {
        Header header = inputImage.getHeader();
        java.util.List<java.util.List<RgbConvertable>> pixels = new ArrayList<>();

        Color threshold = new Color(Math.min(256/getBitRate(), 255), Math.min(256/getBitRate(), 255), Math.min(256/getBitRate(), 255));
        for (int i = 0; i < inputImage.getHeight(); ++i){
            List<RgbConvertable> row = new ArrayList<>();
            for (int j = 0; j < inputImage.getWidth(); ++j){
                var pixel = inputImage.getPixel(j, i);
                float factor = (M[i % 8][j % 8] / 64.f) - 0.5f;
                Color attempt = new Color(
                        ((int)Math.max(((int)Math.min(((int)((float)pixel.Red()) + factor * ((float)threshold.getRed())), 255)), 0)),
                        ((int)Math.max(((int)Math.min(((int)((float)pixel.Green()) + factor * ((float)threshold.getGreen())), 255)), 0)),
                        ((int)Math.max(((int)Math.min(((int)((float)pixel.Blue()) + factor * ((float)threshold.getBlue())), 255)), 0)));

                row.add(new RgbPixel(((float)findClosest(attempt.getRed())) / 255.f,
                        ((float)findClosest(attempt.getGreen())) / 255.f,
                        ((float)findClosest(attempt.getBlue())) / 255.f));
            }
            pixels.add(row);
        }
        return new PnmFile(header, pixels);
    }

    @Override
    public boolean isInLineGamma() {
        return true;
    }

    private int findClosest(int color){
        int step = (int) (256.f / (Math.pow(2, getBitRate()) - 1));
        if (color % step < step - (color % step))
            return Math.max(color - color % step, 0);
        else
            return Math.min(color + step - (color % step), 255);
    }
}