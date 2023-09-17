package util;

import entities.PnmFile;

import java.io.FileWriter;
import java.io.IOException;

public class PnmFileWriter {
    public void save(PnmFile pnmFile) throws IOException {
        saveAs(pnmFile, pnmFile.getName());
    }

    public void saveAs(PnmFile pnmFile, String fileName) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);

        // use PnmImageDataEncoder implementation to convert pixels into chars
    }
}
