package ru.shurshavchiki.businessLogic.imageProcessing.autocorrection;

import ru.shurshavchiki.businessLogic.colorSpace.models.SingleChannelUnit;

import java.util.ArrayList;
import java.util.List;

public class PlainContrastCorrector implements ContrastCorrector {
    private final float upperBoundary;
    private final float lowerBoundary;

    public PlainContrastCorrector(float upperBoundary, float lowerBoundary) {
        this.upperBoundary = upperBoundary;
        this.lowerBoundary = lowerBoundary;
    }

    @Override
    public float calculateLowerLimit(List<Histogram> histograms) {
        int discardedPixelMaxAmount = (int) (histograms.get(0).ValueDistribution().stream().reduce(0, Integer::sum) * lowerBoundary);
        int minValue = 256;

        for (var histogram: histograms) {
            int totalAmount = 0;
            for (int i = 0; i < histogram.ValueDistribution().size(); i++) {
                totalAmount += histogram.ValueDistribution().get(i);
                if (totalAmount > discardedPixelMaxAmount) {
                    minValue = Math.min(minValue, i);
                    break;
                }
            }
        }

        return minValue / 255f;
    }

    @Override
    public float calculateUpperLimit(List<Histogram> histograms) {
        int discardedPixelMaxAmount = (int) (histograms.get(0).ValueDistribution().stream().reduce(0, Integer::sum) * upperBoundary);
        int maxValue = 0;
        for (var histogram: histograms) {
            int totalAmount = 0;
            for (int i = histogram.ValueDistribution().size() - 1; i >= 0; i--) {
                totalAmount += histogram.ValueDistribution().get(i);
                if (totalAmount > discardedPixelMaxAmount) {
                    maxValue = Math.max(maxValue, i);
                    break;
                }
            }
        }

        return maxValue / 255f;
    }

    @Override
    public List<SingleChannelUnit> correctContrast(List<SingleChannelUnit> channelUnits, float lowerLimit, float upperLimit) {
        List<SingleChannelUnit> result = new ArrayList<>();
        for (var unit: channelUnits) {
            result.add(correctContrast(unit, lowerLimit, upperLimit));
        }

        return result;
    }

    @Override
    public SingleChannelUnit correctContrast(SingleChannelUnit channelUnit, float lowerLimit, float upperLimit) {
        System.out.println("Upper limit: " + upperLimit + ", lower limit: " + lowerLimit);
        Float[] updatedValues = new Float[channelUnit.Values().length];
        for (int i = 0; i < channelUnit.Values().length; i++) {
            updatedValues[i] = calculateCorrectedValue(channelUnit.Values()[i], lowerLimit, upperLimit);
        }

        return new  SingleChannelUnit(channelUnit.Channel(), updatedValues);
    }

    private float calculateCorrectedValue(float startValue, float lowerLimit, float upperLimit) {
        float result = (startValue - lowerLimit) / (upperLimit - lowerLimit);
        return Math.min(Math.max(result, 0f), 1f);
    }
}
