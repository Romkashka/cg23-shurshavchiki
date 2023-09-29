package ru.shurshavchiki.businessLogic.services;

import ru.shurshavchiki.businessLogic.entities.Displayable;
import ru.shurshavchiki.businessLogic.exceptions.OpenFileException;
import ru.shurshavchiki.businessLogic.models.Header;
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

        return new PnmFileBuilder(file).getFile();
    }

    public void saveFile(Displayable displayable, File file) throws IOException {
        new PnmFileWriter().save(displayable, file);
    }

    public void saveFromRawData(File file, Header header, byte[] data) throws IOException {
        new PnmFileWriter().saveFromRawData(file, header, data);
    }
}
