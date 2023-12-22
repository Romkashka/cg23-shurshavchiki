package ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations;

import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.FloatAlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.IntegerAlgorithmParameter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.exceptions.FilterException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UnsharpFilter extends ImageLinearFilterBase {
    protected GaussianBlurFilter blurFilter;
    protected float minDifference;
    @Override
    public String getName() {
        return "Unsharp mask";
    }

    @Override
    public void init(List<AlgorithmParameter> parameterList) {
        if (parameterList.size() != 4) {
            throw FilterException.InvalidParametersList();
        }

        float amount = extractFloatValue(parameterList.get(0));
        float sigma = extractFloatValue(parameterList.get(1));
        int threshold = extractIntValue(parameterList.get(2));
        int value = extractIntValue(parameterList.get(3));

        isGrayFilter = value == 1;

        minDifference = (float) threshold / 255f;

        blurFilter = new GaussianBlurFilter();
        blurFilter.init(List.of(new FloatAlgorithmParameter("Sigma", 0f, 5f, sigma),
                new IntegerAlgorithmParameter("Is monochrome", 0, 1, value)));

        maskRadius = blurFilter.maskRadius;

        mask = new float[blurFilter.mask.length][blurFilter.mask.length];

        float[][] equalityMask = new float[mask.length][mask.length];

        for (int i = 0; i < equalityMask.length; i++) {
            for (int j = 0; j < equalityMask.length; j++) {
                equalityMask[i][j] = 0f;
            }
        }

        equalityMask[maskRadius][maskRadius] = 1f;

        coefficient = 0f;

        for (int i = 0; i < mask.length; i++) {
            for (int j = 0; j < mask.length; j++) {
                mask[i][j] = equalityMask[i][j] + (equalityMask[i][j] - blurFilter.mask[i][j] * amount) / amount;
                coefficient += mask[i][j];
            }
        }
        coefficient = 1f / coefficient;
    }

    @Override
    protected boolean isProcessingNeeded(int x, int y) {
        RgbConvertable current = grayscaleSource.getPixel(x, y);
        RgbConvertable resultPixel = applyMask(x, y);

        return diff(current, resultPixel) >= minDifference;
    }

    protected float diff(RgbConvertable current, RgbConvertable neighbour) {
        float result = Math.max(Math.abs(current.FloatRed() - neighbour.FloatRed()),
                Math.max(Math.abs(current.FloatGreen() - neighbour.FloatGreen()),
                        Math.abs(current.FloatBlue() - neighbour.FloatBlue())));

        return result;
    }

    @Override
    protected Displayable normalize(Displayable displayable) {
        List<List<RgbConvertable>> result = new ArrayList<>();

        for (var row: displayable.getAllPixels()) {
            List<RgbConvertable> currentRow = new ArrayList<>();
            for (var pixel: row) {
                RgbConvertable newPixel = new RgbPixel(Math.min(1f, Math.max(0f, pixel.FloatRed())),
                        Math.min(1f, Math.max(0f, pixel.FloatGreen())), Math.min(1f, Math.max(0f, pixel.FloatBlue())));

                currentRow.add(newPixel);
            }
            result.add(currentRow);
        }

        return new PnmFile(displayable.getHeader(), result);
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return List.of(
                new FloatAlgorithmParameter("Amount", 0.01f, 5f, 0.5f),
                new FloatAlgorithmParameter("Sigma", 0.1f, 12f, 5f),
                new IntegerAlgorithmParameter("Threshold", 0, 256, 0),
                new IntegerAlgorithmParameter("Is monochrome", 0, 1, 0)
        );
    }
}
