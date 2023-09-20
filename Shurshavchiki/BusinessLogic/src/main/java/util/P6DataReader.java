package util;

import lombok.NonNull;
import models.Header;
import models.RgbConvertable;
import models.RgbPixel;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;

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
    public @NonNull RgbConvertable nextPixel() throws IOException {
        int red, green, blue;
        if (header.getMaxValue() < 256){
            red = dataInputStream.readUnsignedByte();
            green = dataInputStream.readUnsignedByte();;
            blue = dataInputStream.readUnsignedByte();
        } else {
            red = dataInputStream.readUnsignedShort();
            green = dataInputStream.readUnsignedShort();;
            blue = dataInputStream.readUnsignedShort();
        }
        RgbPixel rgbPixel = new RgbPixel(red, green, blue);
        readPixels++;
        return rgbPixel;
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
