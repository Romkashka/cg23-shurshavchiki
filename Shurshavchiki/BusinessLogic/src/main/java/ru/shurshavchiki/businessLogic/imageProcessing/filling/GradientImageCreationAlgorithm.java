package ru.shurshavchiki.businessLogic.imageProcessing.filling;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;

import java.util.ArrayList;
import java.util.List;

public class GradientImageCreationAlgorithm implements ImageCreationAlgorithm {
    @Override
    public String getName() {
        return "Gradient";
    }

    @Override
    public Displayable create(int height, int width) {
        Header header = new Header("P6", width, height, 255);

        List<RgbConvertable> template = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            template.add(new RgbPixel((float) i / (float) (width - 1)));
        }

        List<List<RgbConvertable>> pixels = new ArrayList<>();
        for (int i = 0; i < height; i++) {
            pixels.add(List.copyOf(template));
        }

        return new PnmFile(header, pixels);
    }
}
