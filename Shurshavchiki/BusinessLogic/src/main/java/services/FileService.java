package services;

import entities.Displayable;
import entities.PnmDisplayable;
import entities.PnmFile;
import exceptions.OpenFileException;
import util.PnmFileBuilder;
import util.PnmFileWriter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FileService {
    public PnmDisplayable readFile(File file) throws IOException {
        System.out.println("service read");
        if (!file.isFile()) {
            throw OpenFileException.fileIsCorrupted(file.getName());
        }

        if (!file.canRead()) {
            throw OpenFileException.fileCantBeRead(file.getName());
        }

        return new PnmFileBuilder(file).GetFile();
    }

    public void saveFile(Displayable pnmFile, File destination) throws IOException {
        new PnmFileWriter().saveAs(pnmFile, destination);
    }
}
