package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;

import java.io.File;
import java.io.IOException;

public interface FileService {
    Displayable readFile(File file, ColorSpaceConverter colorSpaceConverter, ChannelChooser channelChooser) throws IOException;
    void saveFile(Displayable displayable, File file, ColorSpaceConverter colorSpaceConverter, ChannelChooser channelChooser    ) throws IOException;
    Displayable applyColorFilters(Displayable source, ColorSpaceConverter colorSpaceConverter, ChannelChooser channelChooser);
    Displayable assignGamma(Displayable source, GammaConverter gammaConverter);
    Displayable convertGamma(Displayable source, GammaConverter gammaConverter);
}
