package util;

import entities.Displayable;
import entities.PnmDisplayable;
import entities.PnmFile;
import exceptions.WriteFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PnmFileWriter {

    public void saveAs(Displayable displayableFile, File file) throws IOException {
        if (!(displayableFile instanceof PnmDisplayable)) {
            throw WriteFileException.unsupportedFileFormat();
        }

        PnmDisplayable pnmFile = (PnmDisplayable) displayableFile;

        System.out.println("converted");

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        writeHeader(fileOutputStream, pnmFile);
        PnmImageDataEncoder dataEncoder = chooseDataEncoder(pnmFile);
        fileOutputStream.write(dataEncoder.createCharBuffer());
        fileOutputStream.close();
    }

    private void writeHeader(FileOutputStream fileOutputStream, PnmDisplayable pnmFile) throws IOException {
        fileOutputStream.write((pnmFile.getVersion() + "\n").getBytes());
        fileOutputStream.write((pnmFile.getWidth() + " " + pnmFile.getHeight() + "\n").getBytes());
        fileOutputStream.write((pnmFile.getMaxval() + "\n").getBytes());
    }

    private PnmImageDataEncoder chooseDataEncoder(PnmDisplayable pnmFile) {
        return switch (pnmFile.getVersion()) {
            case "P5" -> new P5DataEncoder(pnmFile);
            case "P6" -> new P6DataEncoder(pnmFile);
            default -> throw WriteFileException.unsupportedFileVersion(pnmFile.getVersion());
        };
    }
}
