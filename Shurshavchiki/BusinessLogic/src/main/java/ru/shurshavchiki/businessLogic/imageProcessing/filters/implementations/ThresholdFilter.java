package ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations;

import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.IntegerAlgorithmParameter;
import ru.shurshavchiki.businessLogic.exceptions.FilterException;

import java.util.List;

public class ThresholdFilter extends ImageLinearFilterBase {
    float threshold1;
    float threshold2;

    @Override
    public String getName() {
        return "Threshold Filter";
    }

    @Override
    public void init(List<AlgorithmParameter> parameterList) {
        if (parameterList.size() != 2) {
            throw FilterException.InvalidParametersList();
        }

        int value1 = extractIntValue(parameterList.get(0));
        int value2 = extractIntValue(parameterList.get(1));

        if (value2 < value1) {
            int tmp = value1;
            value1 = value2;
            value2 = tmp;
        }

        threshold1 = gammaConverter.useGamma(value1 / 255f);
        threshold2 = gammaConverter.useGamma(value2 / 255f);

        maskRadius = 0;
        coefficient = 1f;
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return List.of(
                new IntegerAlgorithmParameter("Threshold 1", 0, 256, 100),
                new IntegerAlgorithmParameter("Threshold 2", 0, 256, 200)
        );
    }

    @Override 
    public Displayable applyFilter(Displayable source) {
        Displayable newDisplayable = source.clone();
        List<List<RgbConvertable>> pixels = newDisplayable.getAllPixels();
        for (int y = 0; y < source.getHeight(); y++){
            for (int x = 0; x < source.getWidth(); x++){
                RgbConvertable pixel = pixels.get(y).get(x);
                RgbPixel newPixel = new RgbPixel(
                    applyMaskValue(threshold1, threshold2, pixel.FloatRed),
                    applyMaskValue(threshold1, threshold2, pixel.FloatGreen),
                    applyMaskValue(threshold1, threshold2, pixel.FloatBlue)
                );
                newDisplayable.setPixel(x, y, newPixel);
            }
        }
        return newDisplayable;
    }

    @Override
    protected float applyMaskValue(int i, int j, float sourceValue) {
        if (sourceValue < threshold1) {
            return 0f;
        }
        else if (sourceValue < threshold2) {
            return 0.5f;
        }
        else {
            return 1f;
        }
    }
}
