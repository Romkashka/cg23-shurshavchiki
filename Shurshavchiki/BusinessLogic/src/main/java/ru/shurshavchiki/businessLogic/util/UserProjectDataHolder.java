package ru.shurshavchiki.businessLogic.util;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.entities.Displayable;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;

import java.io.File;

public interface UserProjectDataHolder {
    File getFile();
    Displayable getDisplayable();
    GammaConverter getGammaConverter();
    ColorSpaceConverter getColorSpaceConverter();
    ChannelChooser getChannelChooser();
    Displayable getStartingDisplayable();
    ColorSpaceConverter getStartingColorSpaceConverter();
    ChannelChooser getStartingChannelChooser();

    void setFile(File file);
    void setDisplayable(Displayable displayable);
    void setGammaConverter(GammaConverter gammaConverter);
    void setColorSpaceConverter(ColorSpaceConverter colorSpaceConverter);
    void setChannelChooser(ChannelChooser channelChooser);
    void setStartingDisplayable(Displayable displayable);
    void setStartingColorSpaceConverter(ColorSpaceConverter converter);
    void setStartingChannelChooser(ChannelChooser channelChooser);
}
