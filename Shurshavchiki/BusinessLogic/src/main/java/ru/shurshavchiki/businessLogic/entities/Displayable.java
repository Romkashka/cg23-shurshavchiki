package ru.shurshavchiki.businessLogic.entities;

import lombok.NonNull;
import ru.shurshavchiki.businessLogic.models.RgbConvertable;

public interface Displayable {
    int getHeight();
    int getWidth();

    @NonNull RgbConvertable getPixel(int x, int y);
}
