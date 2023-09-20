package models;

public record MonochromePixel(int Gray) implements RgbConvertable {
    @Override
    public int Red() {
        return Gray;
    }

    @Override
    public int Green() {
        return Gray;
    }

    @Override
    public int Blue() {
        return Gray;
    }
}
