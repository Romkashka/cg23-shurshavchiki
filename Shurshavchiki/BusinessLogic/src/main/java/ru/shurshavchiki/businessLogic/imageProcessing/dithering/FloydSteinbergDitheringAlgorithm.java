package ru.shurshavchiki.businessLogic.imageProcessing.dithering;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FloydSteinbergDitheringAlgorithm extends DitheringAlgorithmBase{

    @Override
    public String getName() {
        return "Floyd-Steinberg";
    }

    @Override
    public Displayable applyDithering(Displayable inputImage) {
        Header header = inputImage.getHeader();
        List<List<RgbConvertable>> newPixels = new ArrayList<>();
        List<List<RgbConvertable>> oldPixels = inputImage.getAllPixels();

        Color threshold = new Color(Math.min(256/getBitRate(), 255), Math.min(256/getBitRate(), 255), Math.min(256/getBitRate(), 255));
        for (int i = 0; i < inputImage.getHeight(); ++i){
            List<RgbConvertable> row = new ArrayList<>();
            for (int j = 0; j < inputImage.getWidth(); ++j){
                var pixel = oldPixels.get(j).get(i);

                int newPixelRed = findClosest(pixel.Red());
                int newPixelGreen = findClosest(pixel.Green());
                int newPixelBlue = findClosest(pixel.Blue());
                row.add(new RgbPixel(((float)newPixelRed) / 255.f,
                        ((float)newPixelGreen) / 255.f,
                        ((float)newPixelBlue) / 255.f));

                float errRed = pixel.FloatRed() - (float)newPixelRed / 255;
                float errGreen = pixel.FloatGreen() - (float)newPixelGreen / 255;
                float errBlue = pixel.FloatBlue() - (float)newPixelBlue / 255;
                if (i < inputImage.getHeight() - 1){
                    oldPixels.get(j).set(i + 1, new RgbPixel(
                            Math.max(Math.min(oldPixels.get(j).get(i + 1).FloatRed() + errRed * 5.f / 16.f, 255), 0),
                            Math.max(Math.min(oldPixels.get(j).get(i + 1).FloatGreen() + errGreen * 5.f / 16.f, 255), 0),
                            Math.max(Math.min(oldPixels.get(j).get(i + 1).FloatBlue() + errBlue * 5.f / 16.f,  255), 0)));
                    if (j < inputImage.getWidth() - 1){
                        oldPixels.get(j + 1).set(i + 1, new RgbPixel(
                                Math.max(Math.min(oldPixels.get(j + 1).get(i + 1).FloatRed() + errRed / 16.f, 255), 0),
                                Math.max(Math.min(oldPixels.get(j + 1).get(i + 1).FloatGreen() + errGreen / 16.f, 255), 0),
                                Math.max(Math.min(oldPixels.get(j + 1).get(i + 1).FloatBlue() + errBlue / 16.f,  255), 0)));
                    }
                    if (j > 0){
                        oldPixels.get(j - 1).set(i + 1, new RgbPixel(
                                Math.max(Math.min(oldPixels.get(j - 1).get(i + 1).FloatRed() + errRed * 3.f / 16.f, 255), 0),
                                Math.max(Math.min(oldPixels.get(j - 1).get(i + 1).FloatGreen() + errGreen * 3.f / 16.f, 255), 0),
                                Math.max(Math.min(oldPixels.get(j - 1).get(i + 1).FloatBlue() + errBlue * 3.f / 16.f,  255), 0)));
                    }
                }
                if (j < inputImage.getWidth() - 1){
                    oldPixels.get(j + 1).set(i, new RgbPixel(
                            Math.max(Math.min(oldPixels.get(j + 1).get(i).FloatRed() + errRed * 7.f / 16.f, 255), 0),
                            Math.max(Math.min(oldPixels.get(j + 1).get(i).FloatGreen() + errGreen * 7.f / 16.f, 255), 0),
                            Math.max(Math.min(oldPixels.get(j + 1).get(i).FloatBlue() + errBlue * 7.f / 16.f, 255), 0)));
                }
            }
            newPixels.add(row);
        }
        return new PnmFile(header, newPixels);
    }

    private int findClosest(int color){
        int step = (int) (256.f / (Math.pow(2, getBitRate()) - 1));
        if (color % step < step - (color % step))
            return Math.max(color - color % step, 0);
        else
            return Math.min(color + step - (color % step), 255);
    }
}