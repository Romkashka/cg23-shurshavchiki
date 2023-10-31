package ru.shurshavchiki.businessLogic.domain.io;

import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;

import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class P5DataReader implements PixelDataReader {

    private Header header;
    private DataInputStream dataInputStream;

    private int totalPixels;
    private int readPixels;

    public P5DataReader(Header header, java.io.File file) throws IOException {
        this.header = header;
        this.totalPixels = this.header.getHeight() * this.header.getWidth();
        FileInputStream fileInputStream = new FileInputStream(file);
        this.dataInputStream = new DataInputStream(fileInputStream);

        int numnewlines = 3;
        while (numnewlines > 0) {
            char c;
            do {
                c = (char) (dataInputStream.readUnsignedByte());
            } while (c != '\n');
            numnewlines--;
        }
    }

    @Override
    public ArrayList<RgbConvertable> nextPixel() throws IOException {
        ArrayList<RgbConvertable> monochromePixelArrayList = new ArrayList<>();
        byte[] byteData = new byte[header.getHeight() * header.getWidth() * (((header.getMaxValue() < 256) ? 1 : 0) + 1)];
        dataInputStream.read(byteData);
        if (header.getMaxValue() < 256) {
            for (int i = 0; i < header.getHeight() * header.getWidth(); i++) {
                monochromePixelArrayList.add(new RgbPixel(byteData[i] & 0xff));
            }
        } else {
            for (int i = 0; i < header.getHeight() * header.getWidth(); i++) {
                monochromePixelArrayList.add(new RgbPixel((byteData[2 * i]) & 0xff * 256 + byteData[2 * i + 1] & 0xff));
            }
        }
        return normalize(monochromePixelArrayList);
    }

    @Override
    public float[] getFloatPixels() throws IOException {
        float[] floatPixelsArray = new float[totalPixels * 3];
        byte[] byteData = new byte[header.getHeight() * header.getWidth() * (((header.getMaxValue() < 256) ? 1 : 0) + 1)];
        dataInputStream.read(byteData);
        if (header.getMaxValue() < 256) {
            for (int i = 0; i < header.getHeight() * header.getWidth(); i++) {
                floatPixelsArray[3*i] = (float) (byteData[i] & 0xff);
                floatPixelsArray[3*i+1] = (float) (byteData[i] & 0xff);
                floatPixelsArray[3*i+2] = (float) (byteData[i] & 0xff);
            }
        } else {
            for (int i = 0; i < header.getHeight() * header.getWidth(); i++) {
                floatPixelsArray[i] = (float) ((byteData[2 * i]) & 0xff * 256 + byteData[2 * i + 1] & 0xff);
                floatPixelsArray[i+1] = (float) ((byteData[2 * i]) & 0xff * 256 + byteData[2 * i + 1] & 0xff);
                floatPixelsArray[i+2] = (float) ((byteData[2 * i]) & 0xff * 256 + byteData[2 * i + 1] & 0xff);
            }
        }

        float[] result = new float[floatPixelsArray.length];
        for (int i = 0; i < floatPixelsArray.length; i++) {
            result[i] = normalizeChannel(floatPixelsArray[i]);
        }
        return result;
    }

    private ArrayList<RgbConvertable> normalize(List<RgbConvertable> rawData) {
        ArrayList<RgbConvertable> result = new ArrayList<>(rawData.size());
        for (RgbConvertable pixel : rawData) {
            result.add(new RgbPixel(pixel.FloatRed() * 255.0F / header.getMaxValue()));
        }

        return result;
    }

    private float normalizeChannel(float value) {
        return (float) (value / header.getMaxValue());
    }

    @Override
    public boolean hasNext() {
        if (readPixels < totalPixels) {
            return true;
        } else {
            return false;
        }
    }
}
