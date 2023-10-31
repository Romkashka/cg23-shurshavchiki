package ru.shurshavchiki.businessLogic.imageProcessing.filling;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;

import java.util.ArrayList;
import java.util.List;

public class BlankImageCreationAlgorithm implements ImageCreationAlgorithm {
    @Override
    public String getName() {
        return "Blank";
    }

    @Override
    public Displayable create(int height, int width) {
        Header header = new Header("P6", width, height, 255);
        List<List<RgbConvertable>> pixels = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            List<RgbConvertable> currentLine = new ArrayList<>();
            for (int j = 0; j < width; j++) {
                currentLine.add(new RgbPixel(1F));
            }
            pixels.add(currentLine);
        }
        return new PnmFile(header, pixels);
    }
}
