package ru.shurshavchiki.businessLogic.entities;

import lombok.NonNull;
import ru.shurshavchiki.businessLogic.models.RgbConvertable;

import java.util.List;

public interface Displayable {
    String getVersion();
    int getHeight();
    int getWidth();

    @NonNull RgbConvertable getPixel(int x, int y);
    List<List<RgbConvertable>> getAllPixels();
}
