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
        if (parameterList.size() != 3) {
            throw FilterException.InvalidParametersList();
        }

        float amount = extractFloatValue(parameterList.get(0));
        float radius = extractFloatValue(parameterList.get(1));
        int threshold = extractIntValue(parameterList.get(2));

        minDifference = threshold / 255f;

        blurFilter = new GaussianBlurFilter();
        blurFilter.init(List.of(new FloatAlgorithmParameter("Sigma", 0f, 5f, radius / 3f)));

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
                mask[i][j] = equalityMask[i][j] + (equalityMask[i][j] - blurFilter.mask[i][j] / amount) * amount;
                coefficient += mask[i][j];
            }

            System.out.println(Arrays.toString(mask[i]));
        }
        coefficient = 1f / coefficient;
    }

    @Override
    protected boolean isProcessingNeeded(int x, int y) {
        List<Float> differences = List.of(
            diffWithNeighbour(x, y, x-1, y),
            diffWithNeighbour(x, y, x, y-1),
            diffWithNeighbour(x, y, x+1, y),
            diffWithNeighbour(x, y, x, y+1)
        );
        differences = differences.stream().sorted().toList();

        return true;

//        return differences.get(3) > minDifference;
    }

    protected float diffWithNeighbour(int x, int y, int neighbourX, int neighbourY) {
        RgbConvertable current = grayscaleSource.getPixel(x, y);
        RgbConvertable neighbour = grayscaleSource.getPixel(returnPixelCoordinatesToBorders(neighbourX, grayscaleSource.getWidth()),
                returnPixelCoordinatesToBorders(neighbourY, grayscaleSource.getHeight()));
        float result = 0f;
        for (int i = 0; i < 3; i++) {
            result = Math.max(result, Math.abs(current.getByIndex(i) - neighbour.getByIndex(i)));
        }

        return result;
    }

    @Override
    protected Displayable normalize(Displayable displayable) {
        List<List<RgbConvertable>> result = new ArrayList<>();

        for (var row: displayable.getAllPixels()) {
            List<RgbConvertable> currentRow = new ArrayList<>();
            for (var pixel: row) {
                RgbConvertable newPixel = new RgbPixel(Math.min(1f, Math.max(0f, pixel.FloatRed())));

                currentRow.add(newPixel);
            }
            result.add(currentRow);
        }

        return new PnmFile(displayable.getHeader(), result);
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return List.of(
                new FloatAlgorithmParameter("Amount", 0f, 5f, 5f),
                new FloatAlgorithmParameter("Radius", 0.1f, 120f, 2.5f),
                new IntegerAlgorithmParameter("Threshold", 0, 256, 0)
        );
    }
}
