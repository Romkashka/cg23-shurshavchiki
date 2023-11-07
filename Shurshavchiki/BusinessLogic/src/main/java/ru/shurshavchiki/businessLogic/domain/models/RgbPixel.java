package ru.shurshavchiki.businessLogic.domain.models;

public class RgbPixel implements RgbConvertable {
    float redComponent;
    float greenComponent;
    float blueComponent;

    public RgbPixel(float redComponent, float greenComponent, float blueComponent) {
        this.redComponent = redComponent;
        this.greenComponent = greenComponent;
        this.blueComponent = blueComponent;
    }

    public RgbPixel(float grayComponent) {
        redComponent = grayComponent;
        greenComponent = grayComponent;
        blueComponent = grayComponent;
    }

    @Override
    public int Red() {
        return (int) (redComponent * 255F);
    }

    @Override
    public int Green() {
        return (int) (greenComponent * 255F);
    }

    @Override
    public int Blue() {
        return (int) (blueComponent * 255F);
    }

    @Override
    public float FloatRed() {
        return redComponent;
    }

    @Override
    public float FloatGreen() {
        return greenComponent;
    }

    @Override
    public float FloatBlue() {
        return blueComponent;
    }

    @Override
    public float getByIndex(int index) {
        return 0;
    }
}
