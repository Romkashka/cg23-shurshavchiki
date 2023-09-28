package ru.shurshavchiki.businessLogic.util;

import ru.shurshavchiki.businessLogic.entities.Displayable;
import ru.shurshavchiki.businessLogic.exceptions.WriteFileException;
import ru.shurshavchiki.businessLogic.models.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class PnmFileWriter {

    public void save(Displayable displayableFile, File file) throws IOException {
        if (displayableFile == null) {
            throw WriteFileException.noFile();
        }

        PnmImageDataEncoder dataEncoder = chooseDataEncoder(displayableFile.getHeader());
        saveFromRawData(file, displayableFile.getHeader(), dataEncoder.createCharBuffer());
    }

    public void saveFromRawData(File file, Header header, byte[] rawData) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        writeHeader(fileOutputStream, header);
        fileOutputStream.write(rawData);
        fileOutputStream.close();
    }

    //TODO: erase magic constant
    private void writeHeader(FileOutputStream fileOutputStream, Header header) throws IOException {
        fileOutputStream.write((header.getMagicNumber() + "\n").getBytes());
        fileOutputStream.write((header.getWidth() + " " + header.getHeight() + "\n").getBytes());
        fileOutputStream.write((255 + "\n").getBytes());
    }

    private PnmImageDataEncoder chooseDataEncoder(Header header) {
        return switch (header.getMagicNumber()) {
            case "P5" -> new P5DataEncoder(header);
            case "P6" -> new P6DataEncoder(header);
            default -> throw WriteFileException.unsupportedFileVersion(header.getMagicNumber());
        };
    }
}
