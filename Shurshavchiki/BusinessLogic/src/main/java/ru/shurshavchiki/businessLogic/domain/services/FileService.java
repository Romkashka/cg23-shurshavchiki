package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.factories.ColorSpaceFactory;
import ru.shurshavchiki.businessLogic.colorSpace.models.SingleChannelUnit;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
    Displayable readFile(File file, ColorSpaceConverter colorSpaceConverter, ChannelChooser channelChooser) throws IOException;
    void saveFile(Displayable displayable, File file, ColorSpaceConverter colorSpaceConverter, ChannelChooser channelChooser) throws IOException;
    Displayable applyColorFilters(Displayable source, ColorSpaceConverter colorSpaceConverter, ChannelChooser channelChooser);
    Displayable assignGamma(Displayable source, GammaConverter gammaConverter);
    Displayable convertGamma(Displayable source, GammaConverter gammaConverter);
    Displayable useGamma(Displayable source, GammaConverter gammaConverter);
    Displayable correctGamma(Displayable source, GammaConverter gammaConverter);
    List<SingleChannelUnit> splitToChannels(Displayable source, ColorSpaceFactory colorSpaceFactory);
    Displayable concatenateChannelUnits(Header header, List<SingleChannelUnit> channelUnits, ColorSpaceFactory colorSpaceFactory);
}
