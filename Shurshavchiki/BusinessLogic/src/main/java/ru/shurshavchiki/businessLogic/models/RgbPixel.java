package ru.shurshavchiki.businessLogic.models;

public record RgbPixel(double RedComponent, double GreenComponent, double BlueComponent) implements RgbConvertable {

    @Override
    public int Red() {
        return (int) RedComponent;
    }

    @Override
    public int Green() {
        return (int) GreenComponent;
    }

    @Override
    public int Blue() {
        return (int) BlueComponent;
    }
}
