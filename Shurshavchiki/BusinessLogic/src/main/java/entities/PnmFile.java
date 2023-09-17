package entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
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

    @Override
    public @NonNull RgbConvertable getPixel(int x, int y) {
        return pixels.get(x).get(y);
    }
}
