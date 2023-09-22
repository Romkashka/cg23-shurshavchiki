package ru.shurshavchiki.businessLogic.util;

import ru.shurshavchiki.businessLogic.models.Header;
import ru.shurshavchiki.businessLogic.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.models.RgbPixel;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class P6DataReader implements PixelDataReader {

    private Header header;
    private DataInputStream dataInputStream;

    private long totalPixels;
    private long readPixels;

    public P6DataReader(Header header, java.io.File file) throws IOException {
        this.header = header;
        this.totalPixels = header.getHeight() * header.getWidth();
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

        ArrayList<RgbConvertable> rgbPixelsList  = new ArrayList<>();
        byte[] byteData = new byte[header.getHeight() * header.getWidth() * 3 * (((header.getMaxValue() < 256) ? 1 : 0) + 1)];
        dataInputStream.read(byteData);;

        int red, green, blue;
        if (header.getMaxValue() < 256){
            for (int i = 0; i < header.getHeight() * header.getWidth() ; i++){
                rgbPixelsList.add(new RgbPixel(byteData[3*i] & 0xff, byteData[3*i+1] & 0xff, byteData[3*i+2] & 0xff));
            }
        } else {
            for (int i = 0; i < header.getHeight() * header.getWidth(); i++){
                rgbPixelsList.add(new RgbPixel((byteData[6*i] & 0xff) * 256 + byteData[6*i+1]  & 0xff, (byteData[6*i+2] & 0xff) * 256 + byteData[6*i+3] & 0xff, (byteData[6*i+4] & 0xff) * 256 + byteData[6*i+5] & 0xff));
            }
        }
        return rgbPixelsList;
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
