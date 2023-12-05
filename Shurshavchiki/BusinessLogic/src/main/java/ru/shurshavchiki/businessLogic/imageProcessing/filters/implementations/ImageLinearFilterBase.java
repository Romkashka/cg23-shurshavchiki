package ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations;

import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;

public abstract class ImageLinearFilterBase extends ImageFilterBase {
    float[][] mask;

    protected RgbConvertable applyMask(int x, int y) {
        float result = 0;
        for (int i = -maskRadius; i <= maskRadius; i++) {
            for (int j = -maskRadius; j <= maskRadius; j++) {
                result += applyMaskValue(i, j, grayscaleSource.getPixel(returnPixelCoordinatesToBorders(i + x, grayscaleSource.getWidth()),
                        returnPixelCoordinatesToBorders(j + y, grayscaleSource.getHeight())).FloatRed());
            }
        }

        return new RgbPixel(result * coefficient);
    }

    protected float applyMaskValue(int i, int j, float sourceValue) {
        return mask[i + maskRadius][j + maskRadius] * sourceValue;
    }
}
