package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface FileService {
    Displayable readFile(File file) throws IOException;
    void saveFile(Displayable displayable, File file) throws IOException;
    List<String> getColorSpacesNames();
    void chooseChannel(List<String> channelNames);
    void chooseColorSpace(String colorSpaceName);
    void assignGamma(float gamma);
    void convertGamma(float gamma);
    void render();
}
