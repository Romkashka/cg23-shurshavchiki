package ru.shurshavchiki.businessLogic.domain.io.pnm;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.io.FileWriter;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.ImageDataHolder;
import ru.shurshavchiki.businessLogic.exceptions.WriteFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PnmFileWriter implements FileWriter {

    public void save(Displayable displayableFile, File file) throws IOException {
        if (displayableFile == null) {
            throw WriteFileException.noDisplayable();
        }

        PnmImageDataEncoder dataEncoder = chooseDataEncoder(displayableFile);
        saveFromRawData(file, new ImageDataHolder(displayableFile.getHeader(), dataEncoder.createCharBuffer()));
    }

    public void saveFromRawData(File file, ImageDataHolder imageDataHolder) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        writeHeader(fileOutputStream, imageDataHolder.getHeader());
        fileOutputStream.write(imageDataHolder.getData());
        fileOutputStream.close();
    }

    private void writeHeader(FileOutputStream fileOutputStream, Header header) throws IOException {
        fileOutputStream.write((header.getMagicNumber() + "\n").getBytes());
        fileOutputStream.write((header.getWidth() + " " + header.getHeight() + "\n").getBytes());
        fileOutputStream.write((255 + "\n").getBytes());
    }

    private PnmImageDataEncoder chooseDataEncoder(Displayable displayable) {
        return switch (displayable.getVersion()) {
            case "P5" -> new P5DataEncoder(displayable);
            case "P6" -> new P6DataEncoder(displayable);
            default -> throw WriteFileException.unsupportedFileVersion(displayable.getVersion());
        };
    }
}
