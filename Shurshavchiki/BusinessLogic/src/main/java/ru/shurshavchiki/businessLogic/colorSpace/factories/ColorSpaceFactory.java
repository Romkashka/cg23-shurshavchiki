package ru.shurshavchiki.businessLogic.colorSpace.factories;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooserBuilder;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;
import ru.shurshavchiki.businessLogic.domain.util.WithName;

public interface ColorSpaceFactory extends WithName {
    ColorSpace getColorSpace();
    ChannelChooserBuilder getChannelChooserBuilder();
    ColorSpaceConverter getColorSpaceConverter();
}
