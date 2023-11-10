package ru.shurshavchiki.businessLogic.imageProcessing.filling;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomImageCreationAlgorithm implements ImageCreationAlgorithm {
    @Override
    public String getName() {
        return "Random";
    }

    @Override
    public Displayable create(int height, int width) {
        Header header = new Header("P6", width, height, 255);
        List<List<RgbConvertable>> pixels = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<RgbConvertable> row = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                row.add(new RgbPixel((float) Math.random(), (float) Math.random(), (float) Math.random()));
            }
            pixels.add(row);
        }

        return new PnmFile(header, pixels);
    }
}
