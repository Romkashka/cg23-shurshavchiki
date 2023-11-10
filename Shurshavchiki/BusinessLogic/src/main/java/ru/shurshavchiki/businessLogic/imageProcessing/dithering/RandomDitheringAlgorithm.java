package ru.shurshavchiki.businessLogic.imageProcessing.dithering;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RandomDitheringAlgorithm extends DitheringAlgorithmBase{

    @Override
    public String getName() {
        return "Random";
    }

    @Override
    public Displayable applyDithering(Displayable inputImage) {
        Header header = inputImage.getHeader();
        List<List<RgbConvertable>> pixels = new ArrayList<>();

        for (int i = 0; i < inputImage.getHeight(); ++i){
            List<RgbConvertable> row = new ArrayList<>();
            for (int j = 0; j < inputImage.getWidth(); ++j){
                var pixel = inputImage.getPixel(j, i);
                int threshold = (((int)(Math.random()*10000)) % (256));
                if ((int)(Math.random()*100) % 2 == 1)
                    threshold *= -1;

                Color attempt = new Color(

                        ((int)Math.max(((int)Math.min(((int)((float)pixel.Red()) + 0.5*((float)threshold)), 255)), 0)),
                        ((int)Math.max(((int)Math.min(((int)((float)pixel.Green()) + 0.5*((float)threshold)), 255)), 0)),
                        ((int)Math.max(((int)Math.min(((int)((float)pixel.Blue()) + 0.5*((float)threshold)), 255)), 0)));

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
        return false;
    }

    private int findClosest(int color){
        int step = (int) (256.f / (Math.pow(2, getBitRate()) - 1));
        if (color % step < step - (color % step))
            return Math.max(color - color % step, 0);
        else
            return Math.min(color + step - (color % step), 255);
    }
}