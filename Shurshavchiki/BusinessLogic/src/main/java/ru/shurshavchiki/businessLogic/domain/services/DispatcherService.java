package ru.shurshavchiki.businessLogic.domain.services;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface DispatcherService {
    UserProjectDataHolder getUserProjectDataHolder();
    void readFile(File file) throws IOException;
    void saveCurrentFile(File file) throws IOException;
    List<String> getColorSpacesNames();
    void chooseChannel(List<String> channelNames);
    void chooseColorSpace(String colorSpaceName);
    void assignGamma(float gamma);
    void convertGamma(float gamma);
}
