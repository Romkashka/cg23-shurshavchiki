package ru.shurshavchiki.businessLogic.gamma.converters;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static java.lang.Math.pow;

@AllArgsConstructor
public class PlainGammaConverter implements GammaConverter {
    @Getter
    private Float gamma;
    @Override
    public float useGamma(float value) {
        return (float) pow(value, gamma);
    }

    @Override
    public float correctGamma(float value) {
        return (float) pow(value, 1 / gamma);
    }

    @Override
    public String getName() {
        return "Plain gamma " + gamma;
    }
}
