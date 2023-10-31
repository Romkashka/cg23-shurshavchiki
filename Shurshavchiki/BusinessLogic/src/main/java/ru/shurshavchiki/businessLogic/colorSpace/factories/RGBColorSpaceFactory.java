package ru.shurshavchiki.businessLogic.colorSpace.factories;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooserBuilder;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.converters.RgbConverter;
import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;

import java.util.List;

public class RGBColorSpaceFactory implements ColorSpaceFactory {
    private static final String COLOR_SPACE_NAME = "RGB";
    @Override
    public String getName() {
        return COLOR_SPACE_NAME;
    }

    @Override
    public ColorSpace getColorSpace() {
        return new ColorSpace(COLOR_SPACE_NAME, List.of(Channel.RED, Channel.GREEN, Channel.BLUE));
    }

    @Override
    public ChannelChooserBuilder getChannelChooserBuilder() {
        return new ChannelChooserBuilder(getColorSpace(), List.of(0F, 0F, 0F));
    }

    @Override
    public ColorSpaceConverter getColorSpaceConverter() {
        return new RgbConverter();
    }
}
