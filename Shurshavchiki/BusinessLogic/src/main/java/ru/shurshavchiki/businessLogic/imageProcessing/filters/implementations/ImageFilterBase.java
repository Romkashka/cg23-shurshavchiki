package ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations;

import lombok.Setter;
import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.FloatAlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.IntegerAlgorithmParameter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.exceptions.FilterException;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;
import ru.shurshavchiki.businessLogic.imageProcessing.filters.ImageFilter;

import java.util.ArrayList;
import java.util.List;

public abstract class ImageFilterBase implements ImageFilter {
    protected Displayable grayscaleSource;
    protected float coefficient;
    protected int maskRadius;
    @Setter
    protected GammaConverter gammaConverter;

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

    protected abstract RgbConvertable applyMask(int x, int y);

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

    protected int returnPixelCoordinatesToBorders(int x, int border) {
        if (x < 0) {
            return 0;
        }

        if (x >= border) {
            return border - 1;
        }

        return x;
    }

    protected int extractIntValue(AlgorithmParameter parameter) {
        if (parameter instanceof IntegerAlgorithmParameter integerAlgorithmParameter) {
            return integerAlgorithmParameter.getValue();
        }

        throw FilterException.InvalidParametersList();
    }

    protected float extractFloatValue(AlgorithmParameter parameter) {
        if (parameter instanceof FloatAlgorithmParameter floatAlgorithmParameter) {
            return floatAlgorithmParameter.getValue();
        }

        throw FilterException.InvalidParametersList();
    }
}
