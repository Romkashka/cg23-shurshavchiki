package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import models.Header;
import models.RgbConvertable;

import java.util.ArrayList;

@AllArgsConstructor
public class PnmFile implements PnmDisplayable {
    @Getter
    private String version;
    @Getter
    private String name;
    @Getter
    private final int height;
    @Getter
    private final int width;
    @Getter
    private final int maxval;
    @Getter
    private ArrayList<ArrayList<RgbConvertable>> pixels;

    public PnmFile(String name, Header header, ArrayList<ArrayList<RgbConvertable>> pixels) {
        version = header.getMagicNumber();
        this.name = name;
        height = header.getHeight();
        width = header.getWidth();
        maxval = header.getMaxValue();
        this.pixels = pixels;
    }

    @Override
    public @NonNull RgbConvertable getPixel(int x, int y) {
        return pixels.get(y).get(x);
    }
}
