package ru.shurshavchiki.businessLogic.domain.io;

import ru.shurshavchiki.businessLogic.domain.models.ImageDataHolder;

import java.io.File;
import java.io.IOException;

public interface FileReader {
    ImageDataHolder getImageDataHolder(File file) throws IOException;
}
