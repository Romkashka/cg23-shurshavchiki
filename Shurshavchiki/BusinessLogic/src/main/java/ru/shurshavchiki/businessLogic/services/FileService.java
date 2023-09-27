package ru.shurshavchiki.businessLogic.services;

import ru.shurshavchiki.businessLogic.entities.Displayable;
import ru.shurshavchiki.businessLogic.entities.PnmDisplayable;
import ru.shurshavchiki.businessLogic.exceptions.OpenFileException;
import ru.shurshavchiki.businessLogic.util.PnmFileBuilder;
import ru.shurshavchiki.businessLogic.util.PnmFileWriter;

import java.io.File;
import java.io.IOException;

public class FileService {
    public Displayable readFile(File file) throws IOException {
        if (!file.isFile()) {
            throw OpenFileException.notAFile(file.getName());
        }

        if (!file.canRead()) {
            throw OpenFileException.fileCantBeRead();
        }

        return new PnmFileBuilder(file).GetFile();
    }

    public void saveFile(Displayable pnmFile, File destination) throws IOException {
        new PnmFileWriter().saveAs(pnmFile, destination);
    }
}
