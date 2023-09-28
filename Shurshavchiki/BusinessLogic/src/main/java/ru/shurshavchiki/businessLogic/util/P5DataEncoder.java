package ru.shurshavchiki.businessLogic.util;

import ru.shurshavchiki.businessLogic.entities.Displayable;
import ru.shurshavchiki.businessLogic.models.Header;
import ru.shurshavchiki.businessLogic.models.RgbConvertable;

public class P5DataEncoder extends AbstractDataEncoder implements PnmImageDataEncoder {
    public P5DataEncoder(Displayable displayable) {
        super(displayable, 1);
    }

    @Override
    protected int convertPixel(RgbConvertable pixel, int offset, byte[] data) {
        return writeColor(pixel.Red(), offset, data);
    }
}
