package util;

import entities.PnmDisplayable;
import entities.PnmFile;
import exceptions.WriteFileException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class PnmFileWriter {
//    public void save(PnmFile pnmFile) throws IOException {
//        saveAs(pnmFile, pnmFile.getName());
//    }

    public void saveAs(PnmDisplayable pnmFile, File file) throws IOException {
        FileWriter fileWriter = new FileWriter(file);
        writeHeader(fileWriter, pnmFile);
        PnmImageDataEncoder dataEncoder = chooseDataEncoder(pnmFile);
        fileWriter.write(dataEncoder.createCharBuffer());
        fileWriter.close();
    }

    private void writeHeader(FileWriter fileWriter, PnmDisplayable pnmFile) throws IOException {
        fileWriter.write(pnmFile.getVersion() + "\n");
        fileWriter.write(pnmFile.getWidth() + " " + pnmFile.getHeight() + "\n");
        fileWriter.write(pnmFile.getMaxval() + "\n");
    }

    private PnmImageDataEncoder chooseDataEncoder(PnmDisplayable pnmFile) {
        switch (pnmFile.getVersion()) {
            case "P5":
                return new P5DataEncoder();
            case "P6":
                return new P6DataEncoder(pnmFile);
            default:
                throw WriteFileException.unsupportedFileVersion(pnmFile.getVersion());
        }
    }
}
