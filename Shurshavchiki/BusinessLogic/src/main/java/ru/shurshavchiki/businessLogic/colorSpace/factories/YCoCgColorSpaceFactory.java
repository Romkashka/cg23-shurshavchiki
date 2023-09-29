package ru.shurshavchiki.businessLogic.colorSpace.factories;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooserBuilder;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
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
        return null;
    }

    @Override
    public ColorSpaceConverter getColorSpaceConverter() {
        return null;
    }
}
