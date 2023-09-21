package util;

import entities.PnmDisplayable;
import exceptions.WriteFileException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PnmFileWriter {

    public void saveAs(PnmDisplayable pnmFile, File file) throws IOException {
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
