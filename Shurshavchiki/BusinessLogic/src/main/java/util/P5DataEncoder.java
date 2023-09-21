package util;

import entities.PnmDisplayable;
import models.RgbConvertable;

public class P5DataEncoder extends AbstractDataEncoder implements PnmImageDataEncoder {
    public P5DataEncoder(PnmDisplayable pnmFile) {
        super(pnmFile);
    }

    @Override
    protected int convertPixel(RgbConvertable pixel, int offset, byte[] data) {
        return writeColor(pixel.Red(), offset, data);
    }
}
