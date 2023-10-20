package ru.shurshavchiki.businessLogic.domain.io;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;

public class P6DataEncoder extends AbstractDataEncoder implements PnmImageDataEncoder {
    public P6DataEncoder(Displayable displayable) {
        super(displayable, 3);
    }

    public int convertPixel(RgbConvertable pixel, int offset, byte[] data) {
        offset = writeColor(pixel.Red(), offset, data);
        offset = writeColor(pixel.Green(), offset, data);
        return writeColor(pixel.Blue(), offset, data);
    }
}
