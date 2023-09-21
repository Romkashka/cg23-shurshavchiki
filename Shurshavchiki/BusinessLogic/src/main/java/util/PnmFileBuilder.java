package util;

import entities.PnmFile;
import lombok.Getter;
import models.Header;
import models.RgbConvertable;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class PnmFileBuilder {
    @Getter
    private Header header;
    private String name;
    private ArrayList<ArrayList<RgbConvertable>> pixels;
    private PixelDataReader dataReader; // selected based on file version

    public PnmFileBuilder(java.io.File pnmFile) throws IOException {
        Header _header = HeaderReader(pnmFile);
        name = pnmFile.getName();
        switch (_header.getMagicNumber()){
            case "P5":
                dataReader = new P5DataReader(_header, pnmFile);
                break;
            case "P6":
                dataReader = new P6DataReader(_header, pnmFile);
                break;
            default:
                throw new UnsupportedOperationException();
        }
        pixels = new ArrayList<>();
        for (int i = 0; i < _header.getHeight(); i++){
            pixels.add(new ArrayList<RgbConvertable>());
        }
        ArrayList<RgbConvertable> pixelData = dataReader.nextPixel();
        for (int y = 0; y < _header.getHeight(); y++){
            for (int x = 0; x < _header.getWidth(); x++){
                pixels.get(y).add(x, pixelData.get(y * _header.getWidth() + x));
            }
        }
    }

    public PnmFile GetFile(){
        PnmFile pnmFile = new PnmFile(name, header, pixels);
        return pnmFile;
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
