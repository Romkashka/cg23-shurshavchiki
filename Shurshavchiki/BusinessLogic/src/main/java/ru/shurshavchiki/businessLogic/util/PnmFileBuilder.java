package ru.shurshavchiki.businessLogic.util;

import ru.shurshavchiki.businessLogic.entities.PnmFile;
import lombok.Getter;
import ru.shurshavchiki.businessLogic.models.Header;
import ru.shurshavchiki.businessLogic.models.RgbConvertable;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PnmFileBuilder {
    @Getter
    private Header header;
    private String name;
    private ArrayList<ArrayList<RgbConvertable>> pixels;

    public PnmFileBuilder(java.io.File pnmFile) throws IOException {
        header = HeaderReader(pnmFile);
        name = pnmFile.getName();
        PixelDataReader dataReader;
        switch (header.getMagicNumber()) {
            case "P5" -> dataReader = new P5DataReader(header, pnmFile);
            case "P6" -> dataReader = new P6DataReader(header, pnmFile);
            default -> throw new UnsupportedOperationException();
        }
        pixels = new ArrayList<>();
        for (int i = 0; i < header.getHeight(); i++){
            pixels.add(new ArrayList<RgbConvertable>());
        }
        ArrayList<RgbConvertable> pixelData = dataReader.nextPixel();
        for (int y = 0; y < header.getHeight(); y++){
            for (int x = 0; x < header.getWidth(); x++){
                pixels.get(y).add(x, pixelData.get(y * header.getWidth() + x));
            }
        }
    }

    public PnmFile GetFile(){
        return new PnmFile(name, header, pixels);
    }

    private Header HeaderReader(java.io.File pnmFile) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(pnmFile);
        Scanner scan = new Scanner(fileInputStream);
        String magicNumber;
        int picWidth, picHeight, maxValue;
        magicNumber = scan.nextLine();
        picWidth = scan.nextInt();
        picHeight = scan.nextInt();
        maxValue = scan.nextInt();
        Header header = new Header(magicNumber, picWidth, picHeight, maxValue);
        fileInputStream.close();
        return header;
    }
}
