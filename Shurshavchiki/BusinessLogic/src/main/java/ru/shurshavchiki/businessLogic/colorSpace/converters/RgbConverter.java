package ru.shurshavchiki.businessLogic.colorSpace.converters;

import ru.shurshavchiki.businessLogic.exceptions.ColorSpaceException;
import ru.shurshavchiki.businessLogic.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.models.RgbPixel;

import java.util.ArrayList;
import java.util.List;

public class RgbConverter implements ColorSpaceConverter {
    private static final int RAW_DATA_LENGTH_IN_BYTES = 3;

    @Override
    public List<RgbConvertable> toRgb(byte[] rawData) {
        if (rawData.length % RAW_DATA_LENGTH_IN_BYTES != 0) {
            throw ColorSpaceException.invalidDataLength();
        }

        List<RgbConvertable> result = new ArrayList<>();
        for (int i = 0; i < rawData.length; i += 3) {
            result.add(new RgbPixel(rawData[i], rawData[i+1], rawData[i+2]));
        }

        return result;
    }

    @Override
    public byte[] toRawData(List<List<RgbConvertable>> pixels) {
        byte[] result = new byte[pixels.size() * pixels.get(0).size() * RAW_DATA_LENGTH_IN_BYTES];

        int currenIndex = 0;
        for (List<RgbConvertable> row : pixels) {
            for (RgbConvertable pixel : row) {
                currenIndex = writeByteWithOffset(result, currenIndex, pixel.Red());
                currenIndex = writeByteWithOffset(result, currenIndex, pixel.Green());
                currenIndex = writeByteWithOffset(result, currenIndex, pixel.Blue());
            }
        }

        return result;
    }

    private int writeByteWithOffset(byte[] destination, int offset, int data) {
        destination[offset] = (byte) data;
        offset++;
        return offset;
    }
}
