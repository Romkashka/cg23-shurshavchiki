package ru.shurshavchiki.businessLogic.util;

import ru.shurshavchiki.businessLogic.entities.Displayable;
import ru.shurshavchiki.businessLogic.entities.PnmDisplayable;
import ru.shurshavchiki.businessLogic.exceptions.WriteFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PnmFileWriter {

    public void saveAs(Displayable displayableFile, File file) throws IOException {
ку        if (!(displayableFile instanceof PnmDisplayable pnmFile)) {
            throw WriteFileException.unsupportedFileFormat();
        }

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
