package entities;

import lombok.NonNull;
import models.RgbConvertable;
import models.RgbPixel;

public interface Displayable {
    int getHeight();
    int getWidth();

    @NonNull RgbConvertable getPixel(int x, int y);
}
