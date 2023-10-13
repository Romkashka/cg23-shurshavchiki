package ru.shurshavchiki.businessLogic.colorSpace.factories;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooserBuilder;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.converters.PredicateBasedConverter;
import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;

import java.util.List;

public class YCbCr601ColorSpaceFactory implements ColorSpaceFactory {
    private static final String COLOR_SPACE_NAME = "YCbCr.601";
    @Override
    public String getColorSpaceName() {
        return COLOR_SPACE_NAME;
    }

    @Override
    public ColorSpace getColorSpace() {
        return new ColorSpace(COLOR_SPACE_NAME, List.of(Channel.Y_VALUE, Channel.CHROMATIC_BLUE, Channel.CHROMATIC_RED));
    }

    @Override
    public ChannelChooserBuilder getChannelChooserBuilder() {
        return new ChannelChooserBuilder(getColorSpace(), List.of(0.5F, 0.5F, 0.5F));
    }

    @Override
    public ColorSpaceConverter getColorSpaceConverter() {
        return new PredicateBasedConverter(
                input -> {
                    float R = Math.max((input[0] + 1.402F * (input[2] - 0.5f)) * 1000f, 0f);
                    float G = Math.max((input[0] - 0.299F * 1.402F / 0.587F * (input[2] - 0.5f) - 0.114F * 1.772F / 0.587F * (input[1] - 0.5f)) * 1000f, 0f);
                    float B = Math.max((input[0] + 1.772F * (input[1] - 0.5f)) * 1000f, 0f);

                    R = Math.min(R, 1000f);
                    G = Math.min(G, 1000f);
                    B = Math.min(B, 1000f);
                    return new Float[]{R / 1000f, G / 1000f, B / 1000f};
                },
                input -> {
                    float Y =  0.299F * input[0] + 0.587F * input[1] + 0.114F * input[2];
                    float Cb = (input[2] - Y) / 1.772F + 0.5f;
                    float Cr = (input[0] - Y) / 1.402F + 0.5f;
                    return new Float[]{Y, Cb, Cr};
                }
        );
    }
}
