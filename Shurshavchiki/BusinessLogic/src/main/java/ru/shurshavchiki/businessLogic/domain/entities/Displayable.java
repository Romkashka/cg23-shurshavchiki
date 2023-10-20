package ru.shurshavchiki.businessLogic.domain.entities;

import lombok.NonNull;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;

import java.util.List;

public interface Displayable {
    String getVersion();
    int getHeight();
    int getWidth();
    Header getHeader();

    @NonNull RgbConvertable getPixel(int x, int y);
    List<List<RgbConvertable>> getAllPixels();

    Displayable clone();
}
