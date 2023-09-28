package ru.shurshavchiki.businessLogic.models;

public class MonochromePixel implements RgbConvertable {
    private double gray;
    public MonochromePixel(double Gray){
        this.gray = Gray;
    }
    @Override
    public int Red() {
        return (int) gray;
    }

    @Override
    public int Green() {
        return (int) gray;
    }

    @Override
    public int Blue() {
        return (int) gray;
    }

    @Override
    public float FloatRed() {
        return (float) gray;
    }

    @Override
    public float FloatGreen() {
        return (float) gray;
    }

    @Override
    public float FloatBlue() {
        return (float) gray;
    }
}
