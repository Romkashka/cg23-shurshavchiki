package ru.shurshavchiki.businessLogic.gamma.converters;

public interface GammaConverter {
    float useGamma(float value);
    float correctGamma(float value);

    String getName();
}
