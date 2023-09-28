package ru.shurshavchiki.businessLogic.models;

public class MonochromePixel implements RgbConvertable {
    private double Gray;
    public MonochromePixel(double Gray){
        this.Gray = Gray;
    }
    @Override
    public int Red() {
        return (int) Gray;
    }

    @Override
    public int Green() {
        return (int) Gray;
    }

    @Override
    public int Blue() {
        return (int) Gray;
    }
}
