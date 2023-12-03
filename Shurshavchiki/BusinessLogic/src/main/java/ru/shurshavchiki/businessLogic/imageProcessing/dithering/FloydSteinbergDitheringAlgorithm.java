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
        Displayable tmp = inputImage.clone();
        List<List<RgbConvertable>> oldPixels = tmp.getAllPixels();

        for (int i = 0; i < oldPixels.size(); ++i){
            List<RgbConvertable> row = new ArrayList<>();
            for (int j = 0; j < oldPixels.get(0).size(); ++j){
                var pixel = oldPixels.get(i).get(j);

                int newPixelRed = findClosest(pixel.Red());
                int newPixelGreen = findClosest(pixel.Green());
                int newPixelBlue = findClosest(pixel.Blue());
                row.add(new RgbPixel(Math.max(Math.min(((float)newPixelRed) / 255.f, 1.f), 0f),
                        Math.max(Math.min(((float)newPixelGreen) / 255.f, 1.f), 0f),
                        Math.max(Math.min(((float)newPixelBlue) / 255.f, 1.f), 0f)));

                float errRed = pixel.FloatRed() - (float)newPixelRed / 255;
                float errGreen = pixel.FloatGreen() - (float)newPixelGreen / 255;
                float errBlue = pixel.FloatBlue() - (float)newPixelBlue / 255;
                if (i < oldPixels.size() - 1){
                    oldPixels.get(i + 1).set(j, new RgbPixel(
                            Math.max(Math.min(oldPixels.get(i + 1).get(j).FloatRed() + errRed * 5.f / 16.f, 255), 0),
                            Math.max(Math.min(oldPixels.get(i + 1).get(j).FloatGreen() + errGreen * 5.f / 16.f, 255), 0),
                            Math.max(Math.min(oldPixels.get(i + 1).get(j).FloatBlue() + errBlue * 5.f / 16.f,  255), 0)));
                    if (j < oldPixels.get(0).size() - 1){
                        oldPixels.get(i + 1).set(j + 1, new RgbPixel(
                                Math.max(Math.min(oldPixels.get(i + 1).get(j + 1).FloatRed() + errRed / 16.f, 255), 0),
                                Math.max(Math.min(oldPixels.get(i + 1).get(j + 1).FloatGreen() + errGreen / 16.f, 255), 0),
                                Math.max(Math.min(oldPixels.get(i + 1).get(j + 1).FloatBlue() + errBlue / 16.f,  255), 0)));
                    }
                    if (j > 0){
                        oldPixels.get(i + 1).set(j - 1, new RgbPixel(
                                Math.max(Math.min(oldPixels.get(i + 1).get(j - 1).FloatRed() + errRed * 3.f / 16.f, 255), 0),
                                Math.max(Math.min(oldPixels.get(i + 1).get(j - 1).FloatGreen() + errGreen * 3.f / 16.f, 255), 0),
                                Math.max(Math.min(oldPixels.get(i + 1).get(j - 1).FloatBlue() + errBlue * 3.f / 16.f,  255), 0)));
                    }
                }
                if (j < oldPixels.get(0).size() - 1){
                    oldPixels.get(i).set(j + 1, new RgbPixel(
                            Math.max(Math.min(oldPixels.get(i).get(j + 1).FloatRed() + errRed * 7.f / 16.f, 255), 0),
                            Math.max(Math.min(oldPixels.get(i).get(j + 1).FloatGreen() + errGreen * 7.f / 16.f, 255), 0),
                            Math.max(Math.min(oldPixels.get(i).get(j + 1).FloatBlue() + errBlue * 7.f / 16.f, 255), 0)));
                }
            }
            newPixels.add(row);
        }
        return new PnmFile(header, newPixels);
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