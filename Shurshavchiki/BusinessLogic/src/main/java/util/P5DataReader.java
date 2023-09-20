package util;

import lombok.NonNull;
import models.Header;
import models.MonochromePixel;
import models.RgbConvertable;

import java.io.*;

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
    public @NonNull RgbConvertable nextPixel() throws IOException {
        MonochromePixel monochromePixel;
        if (header.getMaxValue() < 256){
            monochromePixel = new MonochromePixel(dataInputStream.readUnsignedByte());
        } else {
            monochromePixel = new MonochromePixel(dataInputStream.readUnsignedShort());
        }
        readPixels++;
        return monochromePixel;
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
