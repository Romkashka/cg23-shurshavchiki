package ru.shurshavchiki.businessLogic.domain.io;

import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.ImageDataHolder;

import java.io.File;
import java.io.IOException;

public interface FileWriter {
    void saveFromRawData(File file, ImageDataHolder imageDataHolder) throws IOException;
}
