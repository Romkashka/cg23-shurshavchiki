package ru.shurshavchiki.businessLogic.colorSpace.converters;

import ru.shurshavchiki.businessLogic.models.RgbConvertable;

import java.util.ArrayList;
import java.util.List;

public interface ColorSpaceConverter {
    List<RgbConvertable> toRgb(byte[] rawData);
    byte[] toRawData(List<List<RgbConvertable>> pixels);
}
