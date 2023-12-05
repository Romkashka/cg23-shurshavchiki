package ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations;

import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;

import java.util.ArrayList;
import java.util.List;

public class SobelFilter extends ImageFilterBase {
    protected final float[][] xGradient = new float[][] {{-1f, 0f, 1f}, {-2f, 0f, 2f}, {-1f, 0f, 1f}};
    protected final float[][] yGradient = new float[][] {{1f, 2f, 1f}, {0f, 0f, 0f}, {-1f, -2f, -1f}};

    @Override
    public String getName() {
        return "Sobel Filter";
    }

    @Override
    public void init(List<AlgorithmParameter> parameterList) {
        maskRadius = 1;
        coefficient = 1f;
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return List.of();
    }

    @Override
    protected RgbConvertable applyMask(int x, int y) {
        float xResult = 0;
        float yResult = 0;
        for (int i = -maskRadius; i <= maskRadius; i++) {
            for (int j = -maskRadius; j <= maskRadius; j++) {
                xResult += applyMaskValue(i, j, grayscaleSource.getPixel(returnPixelCoordinatesToBorders(i + x, grayscaleSource.getWidth()),
                        returnPixelCoordinatesToBorders(j + y, grayscaleSource.getHeight())).FloatRed(), xGradient);
                yResult += applyMaskValue(i, j, grayscaleSource.getPixel(returnPixelCoordinatesToBorders(i + x, grayscaleSource.getWidth()),
                        returnPixelCoordinatesToBorders(j + y, grayscaleSource.getHeight())).FloatRed(), yGradient);
            }
        }

        return new RgbPixel((float) Math.sqrt(xResult * xResult + yResult * yResult));
    }

    protected float applyMaskValue(int i, int j, float sourceValue, float[][] mask) {
        return mask[i + maskRadius][j + maskRadius] * sourceValue;
    }

    @Override
    protected Displayable normalize(Displayable displayable) {
        var pixels = displayable.getAllPixels();

        float maxValue = 0f;

        for (var row: pixels) {
            for (var pixel: row) {
                maxValue = Math.max(pixel.FloatRed(), maxValue);
            }
        }

        List<List<RgbConvertable>> result = new ArrayList<>();

        for (var row: pixels) {
            List<RgbConvertable> currentRow = new ArrayList<>();
            for (var pixel: row) {
                RgbConvertable newPixel = new RgbPixel(pixel.FloatRed() / maxValue);

                if (newPixel.FloatRed() > 1f || newPixel.FloatRed() < 0f) {
                    System.out.println("aboba");
                }

                currentRow.add(newPixel);
            }
            result.add(currentRow);
        }

        return new PnmFile(displayable.getHeader(), result);
    }
}
