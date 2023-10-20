package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;

import java.io.File;

public interface UserProjectDataHolder {
    File getFile();
    Displayable getShownDisplayable();
    GammaConverter getInputGammaConverter();
    GammaConverter getShownGammaConverter();
    ColorSpaceConverter getColorSpaceConverter();
    ChannelChooser getChannelChooser();
    Displayable getStartingDisplayable();
    Displayable getDisplayableWithFilters();
    ColorSpaceConverter getStartingColorSpaceConverter();
    ChannelChooser getStartingChannelChooser();

    void setFile(File file);
    void setShownDisplayable(Displayable displayable);
    void setInputGammaConverter(GammaConverter gammaConverter);
    void setShownGammaConverter(GammaConverter gammaConverter);
    void setColorSpaceConverter(ColorSpaceConverter colorSpaceConverter);
    void setChannelChooser(ChannelChooser channelChooser);
    void setStartingDisplayable(Displayable displayable);
    void setDisplayableWithFilters(Displayable displayable);
    void setStartingColorSpaceConverter(ColorSpaceConverter converter);
    void setStartingChannelChooser(ChannelChooser channelChooser);
}
