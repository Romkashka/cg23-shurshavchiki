package ru.shurshavchiki.businessLogic.colorSpace.converters;

import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;

import java.util.List;

public interface ColorSpaceConverter {
    List<RgbConvertable> toRgb(float[] rawData);
    float[] toRawData(List<RgbConvertable> pixels);
}
