package ru.shurshavchiki.businessLogic.imageProcessing.filters;

import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;

import java.util.ArrayList;
import java.util.List;

public abstract class ImageFilterBase implements ImageFilter {
    protected Displayable grayscaleSource;
    protected float coefficient;
    protected float[][] mask;
    protected int maskRadius;

    @Override
    public void init(List<AlgorithmParameter> parameterList) {
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return List.of();
    }

    @Override
    public Displayable applyFilter(Displayable source) {
        this.grayscaleSource = toGrayscale(source);

        Displayable result = grayscaleSource.clone();

        for (int x = 0; x < grayscaleSource.getWidth(); x++) {
            for (int y = 0; y < grayscaleSource.getHeight(); y++) {
                result.setPixel(x, y, applyMask(x, y));
            }
        }

        return result;
    }

    protected RgbConvertable applyMask(int x, int y) {
        float result = 0;
        for (int i = -maskRadius; i <= maskRadius; i++) {
            for (int j = -maskRadius; j <= maskRadius; j++) {
                result += mask[i][j] * grayscaleSource.getPixel(returnToBorders(i + x - maskRadius, grayscaleSource.getWidth()),
                        returnToBorders(j + y - maskRadius, grayscaleSource.getWidth())).FloatRed();
            }
        }

        return new RgbPixel(result * coefficient);
    }

    protected Displayable toGrayscale(Displayable source) {
        List<List<RgbConvertable>> newPixels = new ArrayList<>();

        for (var row: source.getAllPixels()) {
            List<RgbConvertable> newRow = new ArrayList<>();

            for (var pixel: row) {
                newRow.add(toGrayscale(pixel));
            }

            newPixels.add(newRow);
        }

        return new PnmFile(source.getHeader(), newPixels);
    }

    protected RgbConvertable toGrayscale(RgbConvertable pixel) {
        return new RgbPixel(0.3f * pixel.FloatRed() + 0.59f * pixel.FloatGreen() + 0.11f * pixel.FloatBlue());
    }

    protected int returnToBorders(int x, int border) {
        if (x < 0) {
            return 0;
        }

        if (x >= border) {
            return border - 1;
        }

        return x;
    }
}
