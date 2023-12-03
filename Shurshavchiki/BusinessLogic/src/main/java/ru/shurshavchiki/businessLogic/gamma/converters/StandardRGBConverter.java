package ru.shurshavchiki.businessLogic.gamma.converters;

import static java.lang.Math.pow;

public class StandardRGBConverter implements GammaConverter {
    @Override
    public float useGamma(float value) {
        if (value < 0.04045) {
            return value / 12.92F;
        }

        return (float) pow((value + 0.055) / 1.055, 2.4);
    }

    @Override
    public float correctGamma(float value) {
        if (value < 0.0031308) {
            return value * 12.92F;
        }

        return (float) (1.055 * pow(value, 1/2.4) - 0.055);
    }

    @Override
    public Float getGamma() {
        return null;
    }

    @Override
    public String getName() {
        return "sRGB gamma";
    }
}
