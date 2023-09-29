package ru.shurshavchiki.businessLogic.util;

import ru.shurshavchiki.businessLogic.entities.Displayable;
import ru.shurshavchiki.businessLogic.entities.PnmFile;
import lombok.Getter;
import ru.shurshavchiki.businessLogic.exceptions.OpenFileException;
import ru.shurshavchiki.businessLogic.models.Header;
import ru.shurshavchiki.businessLogic.models.RgbConvertable;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PnmFileBuilder {
    @Getter
    private Header header;
    private String name;
    private File file;
    private List<List<RgbConvertable>> pixels;

    public PnmFileBuilder(java.io.File pnmFile) throws IOException {
        file = pnmFile;
        header = readHeader(pnmFile);
        name = pnmFile.getName();
        PixelDataReader dataReader;
        switch (header.getMagicNumber()) {
            case "P5" -> dataReader = new P5DataReader(header, pnmFile);
            case "P6" -> dataReader = new P6DataReader(header, pnmFile);
            default -> throw OpenFileException.unsupportedFileVersion(header.getMagicNumber());
        }
        pixels = new ArrayList<>();
        for (int i = 0; i < header.getHeight(); i++){
            pixels.add(new ArrayList<>());
        }
        ArrayList<RgbConvertable> pixelData = dataReader.nextPixel();
        for (int y = 0; y < header.getHeight(); y++){
            for (int x = 0; x < header.getWidth(); x++){
                pixels.get(y).add(x, pixelData.get(y * header.getWidth() + x));
            }
        }
    }

    public Displayable getFile(){
        return new PnmFile(header, pixels);
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
        if (!magicNumber.equals("P5") && !magicNumber.equals("P6")) {
            throw OpenFileException.fileCantBeRead();
        }

        return magicNumber;
    }
}
