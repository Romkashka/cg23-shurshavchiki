package ru.shurshavchiki.businessLogic.colorSpace.factories;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooserBuilder;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;

import java.util.List;

public class HSVColorSpaceFactory implements ColorSpaceFactory {
    private static final String COLOR_SPACE_NAME = "HSV";

    @Override
    public String getColorSpaceName() {
        return COLOR_SPACE_NAME;
    }

    @Override
    public ColorSpace getColorSpace() {
        return new ColorSpace(COLOR_SPACE_NAME, List.of(Channel.HUE, Channel.SATURATION, Channel.BRIGHTNESS));
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
