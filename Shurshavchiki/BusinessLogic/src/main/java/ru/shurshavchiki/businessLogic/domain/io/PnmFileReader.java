package ru.shurshavchiki.businessLogic.domain.io;

import lombok.Getter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.ImageDataHolder;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.exceptions.OpenFileException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PnmFileReader {
    @Getter
    private final Header header;
    private final File file;

    public PnmFileReader(java.io.File file) throws IOException {
        this.file = file;
        header = readHeader(file);
    }

    public Displayable getDisplayablePnmFile() throws IOException {
        List<List<RgbConvertable>> pixels;
        PixelDataReader dataReader;
        switch (header.getMagicNumber()) {
            case "P5" -> dataReader = new P5DataReader(header, file);
            case "P6" -> dataReader = new P6DataReader(header, file);
            default -> throw OpenFileException.unsupportedFileVersion(header.getMagicNumber());
        }
        pixels = new ArrayList<>();
        for (int i = 0; i < header.getHeight(); i++) {
            pixels.add(new ArrayList<>());
        }
        ArrayList<RgbConvertable> pixelData = dataReader.nextPixel();
        for (int y = 0; y < header.getHeight(); y++) {
            for (int x = 0; x < header.getWidth(); x++) {
                pixels.get(y).add(x, pixelData.get(y * header.getWidth() + x));
            }
        }
        return new PnmFile(header, pixels);
    }

    public ImageDataHolder getImageDataHolder() throws IOException {
        PixelDataReader dataReader;
        switch (header.getMagicNumber()) {
            case "P5" -> dataReader = new P5DataReader(header, file);
            case "P6" -> dataReader = new P6DataReader(header, file);
            default -> throw OpenFileException.unsupportedFileVersion(header.getMagicNumber());
        }
        return new ImageDataHolder(header, dataReader.getFloatPixels());
    }

    private Header readHeader(java.io.File pnmFile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(pnmFile);
        Scanner scan = new Scanner(fileInputStream);
        String magicNumber;
        int picWidth, picHeight, maxValue;
        magicNumber = validateMagicNumber(scan.nextLine());
        picWidth = scan.nextInt();
        picHeight = scan.nextInt();
        maxValue = scan.nextInt();
        Header header = new Header(magicNumber, picWidth, picHeight, maxValue);
        fileInputStream.close();
        return header;
    }

    private String validateMagicNumber(String magicNumber) {
        System.out.println(magicNumber);
        if (!magicNumber.equals("P5") && !magicNumber.equals("P6")) {
            throw OpenFileException.fileCantBeRead();
        }

        return magicNumber;
    }
}
