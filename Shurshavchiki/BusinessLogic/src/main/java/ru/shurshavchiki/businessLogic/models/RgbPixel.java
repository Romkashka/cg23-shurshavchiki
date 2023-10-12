package ru.shurshavchiki.businessLogic.models;

public record RgbPixel(float RedComponent, float GreenComponent, float BlueComponent) implements RgbConvertable {

    @Override
    public int Red() {
        return (int) (RedComponent * 255F);
    }

    @Override
    public int Green() {
        return (int) (GreenComponent * 255F);
    }

    @Override
    public int Blue() {
        return (int) (BlueComponent * 255F);
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
