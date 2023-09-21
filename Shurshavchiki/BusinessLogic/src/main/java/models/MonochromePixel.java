package models;

public class MonochromePixel implements RgbConvertable {
    private int Gray;
    public MonochromePixel(int Gray){
        this.Gray = Gray;
    }
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
