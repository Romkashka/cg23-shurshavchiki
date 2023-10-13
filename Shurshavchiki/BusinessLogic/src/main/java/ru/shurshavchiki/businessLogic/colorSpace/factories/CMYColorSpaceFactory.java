package ru.shurshavchiki.businessLogic.colorSpace.factories;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooserBuilder;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.converters.PredicateBasedConverter;
import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;

import java.util.List;

public class CMYColorSpaceFactory implements ColorSpaceFactory {
    private static final String COLOR_SPACE_NAME = "CMY";
    @Override
    public String getColorSpaceName() {
        return COLOR_SPACE_NAME;
    }

    @Override
    public ColorSpace getColorSpace() {
        return new ColorSpace(COLOR_SPACE_NAME, List.of(Channel.CYAN, Channel.MAGENTA, Channel.YELLOW));
    }

    @Override
    public ChannelChooserBuilder getChannelChooserBuilder() {
        return new ChannelChooserBuilder(getColorSpace(), List.of(0f, 0f, 0f));
    }

    @Override
    public ColorSpaceConverter getColorSpaceConverter() {
        return new PredicateBasedConverter(
                input -> {
                    float R = 1f - input[0];
                    float G = 1f - input[1];
                    float B = 1f - input[2];

                    return new Float[]{R, G, B};
                },
                input -> {
                    float C = 1f - input[0];
                    float M = 1f - input[1];
                    float Y = 1f - input[2];

                    return new Float[]{C, M, Y};
                }
        );
    }
}
