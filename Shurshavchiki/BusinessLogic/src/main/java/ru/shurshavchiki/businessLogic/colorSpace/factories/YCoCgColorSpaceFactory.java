package ru.shurshavchiki.businessLogic.colorSpace.factories;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooserBuilder;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.converters.PredicateBasedConverter;
import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;

import java.util.List;

public class YCoCgColorSpaceFactory implements ColorSpaceFactory {
    private static final String COLOR_SPACE_NAME = "YCoCg";

    @Override
    public String getColorSpaceName() {
        return COLOR_SPACE_NAME;
    }

    @Override
    public ColorSpace getColorSpace() {
        return new ColorSpace(COLOR_SPACE_NAME, List.of(Channel.Y_VALUE, Channel.CHROMATIC_ORANGE, Channel.CHROMATIC_GREEN));
    }

    @Override
    public ChannelChooserBuilder getChannelChooserBuilder() {
        return new ChannelChooserBuilder(getColorSpace(), List.of(0.5f, 0.5f, 0.5f));
    }

    @Override
    public ColorSpaceConverter getColorSpaceConverter() {
        return new PredicateBasedConverter(
                input -> {
                    input[1] -= 0.5f;
                    input[2] -= 0.5f;
                    float R = input[0] + input[1] - input[2];
                    float G = input[0] + input[2];
                    float B = input[0] - input[1] - input[2];

                    R = Math.min(Math.max(R * 1000f, 0f), 1000f) / 1000f;
                    G = Math.min(Math.max(G * 1000f, 0f), 1000f) / 1000f;
                    B = Math.min(Math.max(B * 1000f, 0f), 1000f) / 1000f;

                    return new Float[]{R, G, B};
                },
                input -> {
                    float Y = 0.25f * input[0] + 0.5f * input[1] + 0.25f * input[2];
                    float Co = 0.5f * input[0] - 0.5f * input[2];
                    float Cg = -0.25f * input[0] + 0.5f * input[1] - 0.25f * input[2];

                    Co += 0.5f;
                    Cg += 0.5f;
                    return new Float[]{Y, Co, Cg};
                }
        );
    }
}
