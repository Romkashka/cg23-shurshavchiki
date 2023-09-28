package ru.shurshavchiki.businessLogic.colorSpace.factories;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooserBuilder;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;

public interface ColorSpaceFactory {
    String getColorSpaceName();
    ColorSpace getColorSpace();
    ChannelChooserBuilder getChannelChooserBuilder();
    ColorSpaceConverter getColorSpaceConverter();
}
