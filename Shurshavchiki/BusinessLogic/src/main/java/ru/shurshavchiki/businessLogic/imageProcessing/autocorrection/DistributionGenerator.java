package ru.shurshavchiki.businessLogic.imageProcessing.autocorrection;

import java.util.Arrays;
import java.util.List;

public class DistributionGenerator {
    public List<Integer> getDistribution(Float[] values) {
        Integer[] result = new Integer[256];

        Arrays.fill(result, 0);

        for (float value: values) {
            result[(int) (value * 255f)]++;
        }

        return Arrays.asList(result);
    }
}
