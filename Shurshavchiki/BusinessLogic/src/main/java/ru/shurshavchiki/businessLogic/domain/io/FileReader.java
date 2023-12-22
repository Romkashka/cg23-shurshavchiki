package ru.shurshavchiki.businessLogic.domain.io;

import ru.shurshavchiki.businessLogic.domain.models.ImageDataHolder;

import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

public interface FileReader {
    ImageDataHolder getImageDataHolder(File file) throws IOException, DataFormatException;
}
