package ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations;

import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.FloatAlgorithmParameter;
import ru.shurshavchiki.businessLogic.exceptions.FilterException;

import javax.swing.plaf.basic.BasicBorders;
import java.util.Arrays;
import java.util.List;

public class GaussianBlurFilter extends ImageLinearFilterBase {
    protected float sigma;

    @Override
    public String getName() {
        return "Gaussian Blur";
    }

    @Override
    public void init(List<AlgorithmParameter> parameterList) {
        if (parameterList.size() != 1) {
            throw FilterException.InvalidParametersList();
        }

        sigma = extractFloatValue(parameterList.get(0));

        maskRadius = (int) Math.ceil(3f * sigma);

        mask = new float[2 * maskRadius + 1][2 * maskRadius + 1];

        coefficient = 0;

        for (int i = -maskRadius; i <= maskRadius; i++) {
            for (int j = -maskRadius; j <= maskRadius; j++) {
                float weight = getWeight(i, j);
                mask[i + maskRadius][j + maskRadius] = weight;
                coefficient += weight;
            }
        }

        coefficient = 1f / coefficient;
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return List.of(
                new FloatAlgorithmParameter("Sigma", 0f, 10f, 0.84089642f)
        );
    }

    protected float getWeight(int x, int y) {
        return (float) (Math.exp(-(x * x + y * y) / (2f * sigma * sigma)) / (2f * Math.PI * sigma * sigma));
    }
}
