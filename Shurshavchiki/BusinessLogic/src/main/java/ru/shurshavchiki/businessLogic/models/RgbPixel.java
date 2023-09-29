package ru.shurshavchiki.businessLogic.models;

public record RgbPixel(float RedComponent, float GreenComponent, float BlueComponent) implements RgbConvertable {

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

    @Override
    public float FloatRed() {
        return RedComponent;
    }

    @Override
    public float FloatGreen() {
        return GreenComponent;
    }

    @Override
    public float FloatBlue() {
        return BlueComponent;
    }
}
