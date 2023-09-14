package util;

import entities.AbstractPnmFile;

import java.io.FileWriter;
import java.io.IOException;

public class PnmFileWriter {
    public void save(AbstractPnmFile pnmFile) throws IOException {
        saveAs(pnmFile, pnmFile.getName());
    }

    public void saveAs(AbstractPnmFile pnmFile, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);

        // use PnmImageDataEncoder implementation to convert pixels into chars
    }
}
