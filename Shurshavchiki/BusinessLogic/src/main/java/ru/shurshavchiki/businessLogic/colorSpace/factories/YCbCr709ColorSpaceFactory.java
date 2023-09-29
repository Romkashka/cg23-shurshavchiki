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
        return new ChannelChooserBuilder(getColorSpace(), List.of(0F, 127.5F, 127.5F));
    }

    @Override
    public ColorSpaceConverter getColorSpaceConverter() {
        return new PredicateBasedConverter(
                input -> {
                    float Y =  0.2126F * input[0] + 0.7152F * input[1] + 0.0722F * input[2];
                    float Cb = (input[2] - Y) / 1.8556F + 127.5F;
                    float Cr = (input[0] - Y) / 1.5748F + 127.5F;
                    return new Float[]{Y, Cb, Cr};
                },
                input -> {
                    float R = input[0] + 1.5748F * (input[2] - 127.5F);
                    float G = input[0] - 0.2126F * 1.5748F / 0.7152F * (input[2] - 127.5F) - 0.0722F * 1.8556F / 0.7152F * (input[1] - 127.5F);
                    float B = input[0] + 1.8556F * (input[1] - 127.5F);
                    return new Float[]{R, G, B};
                }
        );
    }
}
