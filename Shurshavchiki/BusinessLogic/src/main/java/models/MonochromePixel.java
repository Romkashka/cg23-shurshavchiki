package models;

public record MonochromePixel(short Gray) implements RgbConvertable {
    @Override
    public short Red() {
        return Gray;
    }

    @Override
    public short Green() {
        return Gray;
    }

    @Override
    public short Blue() {
        return Gray;
    }
}
