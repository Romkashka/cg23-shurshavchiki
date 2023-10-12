package ru.shurshavchiki.businessLogic.colorSpace.converters;

import ru.shurshavchiki.businessLogic.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.models.RgbPixel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class PredicateBasedConverter implements ColorSpaceConverter {
    private Function<Float[], Float[]> toRgb;
    private Function<Float[], Float[]> fromRgb;

    public PredicateBasedConverter(Function<Float[], Float[]> toRgb, Function<Float[], Float[]> fromRgb) {
        this.toRgb = toRgb;
        this.fromRgb = fromRgb;
    }

    @Override
    public List<RgbConvertable> toRgb(float[] rawData) {
        List<RgbConvertable> result = new ArrayList<>();

        for (int i = 0; i < rawData.length; i += 3) {
            Float[] converted = toRgb.apply(new Float[]{rawData[i], rawData[i+1], rawData[i+2]});
            result.add(new RgbPixel(converted[0], converted[1], converted[2]));
        }

        return result;
    }

    @Override
    public float[] toRawData(List<RgbConvertable> pixels) {
        float[] result = new float[pixels.size() * 3];
        int offset = 0;

        for (RgbConvertable pixel: pixels) {
            Float[] converted = fromRgb.apply(new Float[]{pixel.FloatRed(), pixel.FloatGreen(), pixel.FloatBlue()});
            for (int i = 0; i < converted.length; i++, offset++) {
                result[offset] = converted[i];
            }
        }

        return result;
    }
}
