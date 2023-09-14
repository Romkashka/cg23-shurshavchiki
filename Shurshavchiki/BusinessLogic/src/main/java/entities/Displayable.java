package entities;

import lombok.NonNull;
import models.RgbPixel;

public interface Displayable {
    int getHeight();
    int getWidth();
    int getMaxval();

    @NonNull RgbPixel getPixel(int x, int y);
}
