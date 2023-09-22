package ru.shurshavchiki.businessLogic.util;

import ru.shurshavchiki.businessLogic.models.Header;
import ru.shurshavchiki.businessLogic.models.MonochromePixel;
import ru.shurshavchiki.businessLogic.models.RgbConvertable;

import java.io.*;
import java.util.ArrayList;

public class P5DataReader implements PixelDataReader {

    private Header header;
    private DataInputStream dataInputStream;

    private long totalPixels;
    private long readPixels;

    public P5DataReader(Header header, java.io.File file) throws IOException {
        this.header = header;
        this.totalPixels = (long) this.header.getHeight() * this.header.getWidth();
        FileInputStream fileInputStream = new FileInputStream(file);
        this.dataInputStream = new DataInputStream(fileInputStream);

        int numnewlines = 3;
        while (numnewlines > 0) {
            char c;
            do {
                c = (char)(dataInputStream.readUnsignedByte());
            } while (c != '\n');
            numnewlines--;
        }
    }

    @Override
    public ArrayList<RgbConvertable> nextPixel() throws IOException {
        ArrayList<RgbConvertable> monochromePixelArrayList  = new ArrayList<>();
        byte[] byteData = new byte[header.getHeight() * header.getWidth() * (((header.getMaxValue() < 256) ? 1 : 0) + 1)];
        dataInputStream.read(byteData);
        if (header.getMaxValue() < 256){
            for (int i = 0; i < header.getHeight() * header.getWidth(); i++){
                monochromePixelArrayList.add(new MonochromePixel(byteData[i] & 0xff));
            }
        } else {
            for (int i = 0; i < header.getHeight() * header.getWidth(); i++){
                monochromePixelArrayList.add(new MonochromePixel((byteData[2*i]) & 0xff * 256 + byteData[2*i+1] & 0xff));
            }
        }
        return monochromePixelArrayList;
    }

    @Override
    public boolean hasNext() {
        if (readPixels < totalPixels){
            return true;
        }else{
            return false;
        }
    }
}
