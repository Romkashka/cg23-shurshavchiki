package ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations;

import lombok.Getter;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;

public abstract class ImageLinearFilterBase extends ImageFilterBase {
    @Getter
    float[][] mask;

    protected RgbConvertable applyMask(int x, int y) {
        float resultR = 0;
        float resultG = 0;
        float resultB = 0;
        for (int i = -maskRadius; i <= maskRadius; i++) {
            for (int j = -maskRadius; j <= maskRadius; j++) {
                resultR += applyMaskValue(i, j, grayscaleSource.getPixel(returnPixelCoordinatesToBorders(i + x, grayscaleSource.getWidth()),
                        returnPixelCoordinatesToBorders(j + y, grayscaleSource.getHeight())).FloatRed());
                if (!isGrayFilter){
                    resultG += applyMaskValue(i, j, grayscaleSource.getPixel(returnPixelCoordinatesToBorders(i + x, grayscaleSource.getWidth()),
                            returnPixelCoordinatesToBorders(j + y, grayscaleSource.getHeight())).FloatGreen());
                    resultB += applyMaskValue(i, j, grayscaleSource.getPixel(returnPixelCoordinatesToBorders(i + x, grayscaleSource.getWidth()),
                            returnPixelCoordinatesToBorders(j + y, grayscaleSource.getHeight())).FloatBlue());
                }
            }
        }

        if (isGrayFilter)
            return new RgbPixel(resultR * coefficient);

        return new RgbPixel(resultR * coefficient, resultG * coefficient, resultB * coefficient);
    }

    protected float applyMaskValue(int i, int j, float sourceValue) {
        return mask[i + maskRadius][j + maskRadius] * sourceValue;
    }
}
