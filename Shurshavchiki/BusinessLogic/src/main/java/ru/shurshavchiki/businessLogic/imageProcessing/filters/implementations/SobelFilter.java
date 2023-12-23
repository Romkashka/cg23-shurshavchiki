package ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations;

import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.IntegerAlgorithmParameter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.exceptions.FilterException;

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
        if (parameterList.size() != 1) {
            throw FilterException.InvalidParametersList();
        }

        maskRadius = 1;
        coefficient = 1f;
        isGrayFilter = extractIntValue(parameterList.get(0)) == 1;
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return List.of(new IntegerAlgorithmParameter("Is monochrome", 0, 1, 0));
    }

    @Override
    protected RgbConvertable applyMask(int x, int y) {
        float xResultR = 0;
        float yResultR = 0;
        float xResultG = 0;
        float yResultG = 0;
        float xResultB = 0;
        float yResultB = 0;
        for (int i = -maskRadius; i <= maskRadius; i++) {
            for (int j = -maskRadius; j <= maskRadius; j++) {
                xResultR += applyMaskValue(i, j, grayscaleSource.getPixel(returnPixelCoordinatesToBorders(i + x, grayscaleSource.getWidth()),
                        returnPixelCoordinatesToBorders(j + y, grayscaleSource.getHeight())).FloatRed(), xGradient);
                yResultR += applyMaskValue(i, j, grayscaleSource.getPixel(returnPixelCoordinatesToBorders(i + x, grayscaleSource.getWidth()),
                        returnPixelCoordinatesToBorders(j + y, grayscaleSource.getHeight())).FloatRed(), yGradient);
                if (!isGrayFilter){
                    xResultG += applyMaskValue(i, j, grayscaleSource.getPixel(returnPixelCoordinatesToBorders(i + x, grayscaleSource.getWidth()),
                            returnPixelCoordinatesToBorders(j + y, grayscaleSource.getHeight())).FloatGreen(), xGradient);
                    yResultG += applyMaskValue(i, j, grayscaleSource.getPixel(returnPixelCoordinatesToBorders(i + x, grayscaleSource.getWidth()),
                            returnPixelCoordinatesToBorders(j + y, grayscaleSource.getHeight())).FloatGreen(), yGradient);
                    xResultB += applyMaskValue(i, j, grayscaleSource.getPixel(returnPixelCoordinatesToBorders(i + x, grayscaleSource.getWidth()),
                            returnPixelCoordinatesToBorders(j + y, grayscaleSource.getHeight())).FloatBlue(), xGradient);
                    yResultB += applyMaskValue(i, j, grayscaleSource.getPixel(returnPixelCoordinatesToBorders(i + x, grayscaleSource.getWidth()),
                            returnPixelCoordinatesToBorders(j + y, grayscaleSource.getHeight())).FloatBlue(), yGradient);
                }
            }
        }

        if (isGrayFilter)
            return new RgbPixel((float) Math.sqrt(xResultR * xResultR + yResultR * yResultR));

        return new RgbPixel(norm((float) Math.sqrt(xResultR * xResultR + yResultR * yResultR)),
                norm((float) Math.sqrt(xResultG * xResultG + yResultG * yResultG)),
                norm((float) Math.sqrt(xResultB * xResultB + yResultB * yResultB)));
    }

    protected float norm(float v) {
        return Math.max(0, Math.min(1, v));
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
                RgbConvertable newPixel = new RgbPixel(pixel.FloatRed() / maxValue,
                        pixel.FloatGreen() / maxValue, pixel.FloatBlue() / maxValue);

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
