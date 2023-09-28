package ru.shurshavchiki.businessLogic.util;

import ru.shurshavchiki.businessLogic.entities.Displayable;
import ru.shurshavchiki.businessLogic.exceptions.WriteFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PnmFileWriter {

    public void saveAs(Displayable displayableFile, File file) throws IOException {
        if (displayableFile == null) {
            throw WriteFileException.noFile();
        }

        FileOutputStream fileOutputStream = new FileOutputStream(file);
        writeHeader(fileOutputStream, displayableFile);
        PnmImageDataEncoder dataEncoder = chooseDataEncoder(displayableFile);
        fileOutputStream.write(dataEncoder.createCharBuffer());
        fileOutputStream.close();
    }

    //TODO: erase magic constant
    private void writeHeader(FileOutputStream fileOutputStream, Displayable pnmFile) throws IOException {
        fileOutputStream.write((pnmFile.getVersion() + "\n").getBytes());
        fileOutputStream.write((pnmFile.getWidth() + " " + pnmFile.getHeight() + "\n").getBytes());
        fileOutputStream.write((255 + "\n").getBytes());
    }

    private PnmImageDataEncoder chooseDataEncoder(Displayable pnmFile) {
        return switch (pnmFile.getVersion()) {
            case "P5" -> new P5DataEncoder(pnmFile);
            case "P6" -> new P6DataEncoder(pnmFile);
            default -> throw WriteFileException.unsupportedFileVersion(pnmFile.getVersion());
        };
    }
}
