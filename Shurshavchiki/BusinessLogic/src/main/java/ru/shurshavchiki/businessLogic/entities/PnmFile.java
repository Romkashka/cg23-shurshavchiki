package ru.shurshavchiki.businessLogic.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import ru.shurshavchiki.businessLogic.models.Header;
import ru.shurshavchiki.businessLogic.models.RgbConvertable;

import java.util.List;

public class PnmFile implements Displayable {
    @Getter
    private Header header;
    @Getter
    private List<List<RgbConvertable>> pixels;

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
    public List<List<RgbConvertable>> getAllPixels() {
        return pixels;
    }
}
