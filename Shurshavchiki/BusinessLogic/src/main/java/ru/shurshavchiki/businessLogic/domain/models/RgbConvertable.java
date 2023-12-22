package ru.shurshavchiki.businessLogic.domain.models;

public interface RgbConvertable {
    int Red();
    int Green();
    int Blue();

    float FloatRed();
    float FloatGreen();
    float FloatBlue();

    float FloatMean();

    float getByIndex(int index);
}
