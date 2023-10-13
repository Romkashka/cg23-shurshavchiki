package ru.shurshavchiki.businessLogic.colorSpace.factories;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooserBuilder;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.converters.PredicateBasedConverter;
import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;

import java.util.List;

public class YCbCr709ColorSpaceFactory implements ColorSpaceFactory {
    private static final String COLOR_SPACE_NAME = "YCbCr.709";
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
                    float R = Math.max((input[0] + 1.5748f * (input[2] - 0.5f)) * 1000f, 0f);
                    float G = Math.max((input[0] - 0.2126f * 1.5748f / 0.7152f * (input[2] - 0.5f) - 0.0722f * 1.8556f / 0.7152f * (input[1] - 0.5f)) * 1000f, 0f);
                    float B = Math.max((input[0] + 1.8556f * (input[1] - 0.5f)) * 1000f, 0f);

                    R = Math.min(R, 1000f);
                    G = Math.min(G, 1000f);
                    B = Math.min(B, 1000f);
                    return new Float[]{R / 1000f, G / 1000f, B / 1000f};
                },
                input -> {
                    float Y =  0.2126F * input[0] + 0.7152F * input[1] + 0.0722F * input[2];
                    float Cb = (input[2] - Y) / 1.8556F + 0.5f;
                    float Cr = (input[0] - Y) / 1.5748F + 0.5f;

//                    Y = Math.min(Math.max(Y * 1000f, 0f), 1000f);
                    return new Float[]{Y, Cb, Cr};
                }
        );
    }
}
