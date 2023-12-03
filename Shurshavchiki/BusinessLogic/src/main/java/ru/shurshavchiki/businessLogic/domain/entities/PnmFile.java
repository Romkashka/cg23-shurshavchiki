package ru.shurshavchiki.businessLogic.domain.entities;

import lombok.Getter;
import lombok.NonNull;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;

import java.util.ArrayList;
import java.util.List;

public class PnmFile implements Displayable {
    @Getter
    private final Header header;
    @Getter
    private final List<List<RgbConvertable>> pixels;

    public PnmFile(Header header, List<List<RgbConvertable>> pixels) {
        this.header = header;
        this.pixels = pixels;
    }

    @Override
    public String getVersion() {
        return header.getMagicNumber();
    }

    @Override
    public int getHeight() {
        return header.getHeight();
    }

    @Override
    public int getWidth() {
        return header.getWidth();
    }

    @Override
    public @NonNull RgbConvertable getPixel(int x, int y) {
        return pixels.get(y).get(x);
    }

    @Override
    public void setPixel(int x, int y, RgbConvertable pixel) {
        pixels.get(y).set(x, pixel);
    }

    @Override
    public List<List<RgbConvertable>> getAllPixels() {
        return pixels;
    }

    @Override
    public Displayable clone() {
        Header newHeader = new Header(this.header);

        List<List<RgbConvertable>> newPixels = new ArrayList<>();

        for (List<RgbConvertable> row: pixels) {
            List<RgbConvertable> rowCopy = new ArrayList<>();
            for (RgbConvertable pixel: row) {
                rowCopy.add(new RgbPixel(pixel.FloatRed(), pixel.FloatGreen(), pixel.FloatBlue()));
            }
            newPixels.add(rowCopy);
        }

        return new PnmFile(newHeader, newPixels);
    }
}
