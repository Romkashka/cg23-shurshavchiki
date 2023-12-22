package ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations;

import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.IntegerAlgorithmParameter;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.exceptions.FilterException;

import java.util.*;

public class MedianFilter extends ImageFilterBase {
    @Override
    public String getName() {
        return "Median";
    }

    @Override
    public void init(List<AlgorithmParameter> parameterList) {
        if (parameterList.size() != 1) {
            throw FilterException.InvalidParametersList();
        }

        maskRadius = extractIntValue(parameterList.get(0));

        isGrayFilter = false;
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return List.of(
                new IntegerAlgorithmParameter("Mask radius", 1, 100)
        );
    }

    @Override
    protected RgbConvertable applyMask(int x, int y) {
        List<RgbConvertable> pixels = new ArrayList<>();

        for (int i = x - maskRadius; i <= x + maskRadius; i++) {
            for (int j = y - maskRadius; j <= y + maskRadius; j++) {
                pixels.add(grayscaleSource.getPixel(returnPixelCoordinatesToBorders(i, grayscaleSource.getWidth()),
                        returnPixelCoordinatesToBorders(j, grayscaleSource.getHeight())));
            }
        }

        pixels.sort(Comparator.comparingDouble(RgbConvertable::FloatMean));

        return pixels.get(pixels.size() / 2);
    }
}
