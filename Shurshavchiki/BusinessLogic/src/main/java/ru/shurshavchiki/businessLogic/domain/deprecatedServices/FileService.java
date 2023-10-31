package ru.shurshavchiki.businessLogic.domain.deprecatedServices;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
    Displayable readFile(File file) throws IOException;
    void saveFile(Displayable displayable, File file) throws IOException;
    List<String> getColorSpacesNames();
    Displayable chooseChannel(Displayable source, ChannelChooser channelChooser);
    Displayable chooseColorSpace(Displayable source, ColorSpaceConverter colorSpaceConverter);
    Displayable assignGamma(Displayable source, GammaConverter gammaConverter);
    Displayable convertGamma(Displayable source, GammaConverter gammaConverter);
    void render();
}
