package ru.shurshavchiki.businessLogic.util;

import ru.shurshavchiki.businessLogic.entities.Displayable;
import ru.shurshavchiki.businessLogic.models.RgbConvertable;

public class P6DataEncoder extends AbstractDataEncoder implements PnmImageDataEncoder {
    public P6DataEncoder(Displayable pnmFile) {
        super(pnmFile);
        System.out.println("P6");
    }

    public int convertPixel(RgbConvertable pixel, int offset, byte[] data) {
        offset = writeColor(pixel.Red(), offset, data);
        offset = writeColor(pixel.Green(), offset, data);
        return writeColor(pixel.Blue(), offset, data);
    }
}
