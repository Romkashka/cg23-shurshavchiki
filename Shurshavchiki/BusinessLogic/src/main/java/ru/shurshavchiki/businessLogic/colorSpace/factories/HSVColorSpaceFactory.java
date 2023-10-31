package ru.shurshavchiki.businessLogic.colorSpace.factories;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooserBuilder;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.converters.PredicateBasedConverter;
import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HSVColorSpaceFactory implements ColorSpaceFactory {
    private static final String COLOR_SPACE_NAME = "HSV";

    @Override
    public String getName() {
        return COLOR_SPACE_NAME;
    }

    @Override
    public ColorSpace getColorSpace() {
        return new ColorSpace(COLOR_SPACE_NAME, List.of(Channel.HUE, Channel.SATURATION, Channel.BRIGHTNESS));
    }

    @Override
    public ChannelChooserBuilder getChannelChooserBuilder() {
        return new ChannelChooserBuilder(getColorSpace(), List.of(0f, 1f, 1f));
    }

    @Override
    public ColorSpaceConverter getColorSpaceConverter() {
        return new PredicateBasedConverter(
                input -> {
                    float C = input[2] * input[1];
                    float X = C * (1f - Math.abs(input[0] * 6f % 2f - 1f));
                    float m = input[2] - C;
                    Float[] tmp;
                    if (input[0] < 0.16666667f) {
                        tmp = new Float[]{C, X, 0f};
                    }
                    else if (input[0] < 0.33333334f) {
                        tmp = new Float[]{X, C, 0f};
                    }
                    else if (input[0] < 0.5f) {
                        tmp = new Float[]{0f, C, X};
                    }
                    else if (input[0] < 0.66666667f) {
                        tmp = new Float[]{0f, X, C};
                    }
                    else if (input[0] < 0.83333334f) {
                        tmp = new Float[]{X, 0f, C};
                    }
                    else {
                        tmp = new Float[]{C, 0f, X};
                    }

                    return new Float[]{tmp[0] + m, tmp[1] + m, tmp[2] + m};
                },
                input -> {
                    float Cmax = Collections.max(Arrays.asList(input));
                    float Cmin = Collections.min(Arrays.asList(input));
                    float delta = Cmax - Cmin;
                    float H;
                    if (delta == 0) {
                        H = 0F;
                    }
                    else if (Cmax == input[0]) {
                        H = ((6f + (input[1] - input[2]) / delta) % 6f) / 6f;
                    }
                    else if (Cmax == input[1]) {
                        H = ((input[2] - input[0]) / delta + 2f) / 6f;
                    }
                    else {
                        H = ((input[0] - input[1]) / delta + 4f) / 6f;
                    }
                    float S;
                    if (Cmax < 0.000001f ) {
                        S = 0f;
                    }
                    else {
                        S = delta /Cmax;
                    }
                    return new Float[]{H, S, Cmax};
                }
        );
    }
}
