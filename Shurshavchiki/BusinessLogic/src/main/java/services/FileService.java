package services;

import entities.PnmDisplayable;
import entities.PnmFile;
import exceptions.OpenFileException;
import util.PnmFileBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileService {
    public PnmDisplayable readFile(File file) {
        if (file.isFile()) {
            throw OpenFileException.fileIsCorrupted(file.getName());
        }

        if (!file.canRead()) {
            throw OpenFileException.fileCantBeRead(file.getName());
        }

        return new PnmFile("P6", "mockFile.pnm", 0, 0, 0, new ArrayList<>());
    }

    public void saveFile(PnmFile pnmFile, File destination) {

    }
}
