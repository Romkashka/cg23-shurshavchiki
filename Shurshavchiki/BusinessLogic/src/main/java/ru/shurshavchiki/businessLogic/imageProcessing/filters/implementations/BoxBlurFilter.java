package ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations;

import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.IntegerAlgorithmParameter;
import ru.shurshavchiki.businessLogic.exceptions.FilterException;

import java.util.Arrays;
import java.util.List;

public class BoxBlurFilter extends ImageLinearFilterBase {
    @Override
    public String getName() {
        return "Box Blur";
    }

    @Override
    public void init(List<AlgorithmParameter> parameterList) {
        if (parameterList.size() != 2) {
            throw FilterException.InvalidParametersList();
        }

        maskRadius = extractIntValue(parameterList.get(0));
        int value = extractIntValue(parameterList.get(1));
        int maskSize = 2 * maskRadius + 1;

        mask = new float[maskSize][maskSize];
        for (int i = 0; i < maskSize; i++) {
            Arrays.fill(mask[i], 1f);
        }

        coefficient = 1f / (maskSize * maskSize);

        isGrayFilter = value == 1;
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return List.of(
                new IntegerAlgorithmParameter("Radius", 1, 30, 1),
                new IntegerAlgorithmParameter("Is monochrome", 0, 1, 0)
        );
    }
}
