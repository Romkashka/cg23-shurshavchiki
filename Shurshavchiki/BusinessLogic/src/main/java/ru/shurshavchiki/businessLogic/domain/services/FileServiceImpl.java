package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.io.PnmFileReader;
import ru.shurshavchiki.businessLogic.domain.io.PnmFileWriter;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.ImageDataHolder;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.exceptions.ColorSpaceException;
import ru.shurshavchiki.businessLogic.exceptions.OpenFileException;
import ru.shurshavchiki.businessLogic.exceptions.WriteFileException;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FileServiceImpl implements FileService {

    public FileServiceImpl() {
    }

    @Override
    public Displayable readFile(File file, ColorSpaceConverter colorSpaceConverter, ChannelChooser channelChooser) throws IOException {
        checkFileIsReadable(file);
        ImageDataHolder imageDataHolder = new PnmFileReader(file).getImageDataHolder();
        Displayable image = new PnmFile(imageDataHolder.getHeader(),
                splitToRows(imageDataHolder.getHeader(), convertToPixels(imageDataHolder.getData(), colorSpaceConverter, channelChooser)));

        return image;
    }

    @Override
    public void saveFile(Displayable displayable, File file, ColorSpaceConverter colorSpaceConverter, ChannelChooser channelChooser) throws IOException {
        if (file == null) {
            throw WriteFileException.noFile();
        }

        new PnmFileWriter().saveFromRawData(file,
                getHeaderForSave(displayable, channelChooser),
                getByteData(convertToRawData(displayable.getAllPixels(), colorSpaceConverter, channelChooser), channelChooser));
    }

    @Override
    public Displayable applyColorFilters(Displayable source, ColorSpaceConverter colorSpaceConverter, ChannelChooser channelChooser) {
        return null;
    }

    @Override
    public Displayable assignGamma(Displayable source, GammaConverter gammaConverter) {
        throw new UnsupportedOperationException("I will implement it eventually...");
    }

    @Override
    public Displayable convertGamma(Displayable source, GammaConverter gammaConverter) {
        throw new UnsupportedOperationException("I will implement it eventually...");
    }

    private Displayable applyGamma(Displayable displayable, GammaConverter inputGammaConverter, GammaConverter ShownGammaConverter) {
        Displayable result = displayable.clone();
        applyToEachPixel(inputGammaConverter::useGamma, result);
        applyToEachPixel(ShownGammaConverter::correctGamma, result);
        return result;
    }

    private void applyToEachPixel(Function<Float, Float> func, Displayable displayable) {
        for (List<RgbConvertable> row: displayable.getAllPixels()) {
            row.replaceAll(pixel -> applyToPixel(pixel, func));
        }
    }

    private RgbConvertable applyToPixel(RgbConvertable pixel, Function<Float, Float> func) {
        if (pixel instanceof RgbPixel rgbPixel) {
            return new RgbPixel(func.apply(rgbPixel.FloatRed()),
                    func.apply(rgbPixel.FloatGreen()),
                    func.apply(rgbPixel.FloatBlue()));
        }

        throw new UnsupportedOperationException("Only RgbPixel are supported(((");
    }

    private void checkFileIsReadable(File file) {
        if (!file.isFile()) {
            throw OpenFileException.notAFile(file.getName());
        }

        if (!file.canRead()) {
            throw OpenFileException.fileCantBeRead();
        }
    }

    private List<List<RgbConvertable>> splitToRows(Header header, List<RgbConvertable> pixels) {
        if (pixels.size() != header.getHeight() * header.getWidth()) {
            throw ColorSpaceException.invalidDataLength();
        }

        int offset = 0;
        List<List<RgbConvertable>> result = new ArrayList<>();
        for (int i = 0; i < header.getHeight(); i++) {
            List<RgbConvertable> currentRow = new ArrayList<>();
            for (int j = 0; j < header.getWidth(); j++) {
                currentRow.add(pixels.get(offset));
                offset++;
            }
            result.add(currentRow);
        }

        return result;
    }

    private List<RgbConvertable> convertToPixels(float[] rawData, ColorSpaceConverter converter, ChannelChooser channelChooser) {
        return converter.toRgb(channelChooser.fillAllChannels(rawData));
    }

    private float[] convertToRawData(List<List<RgbConvertable>> pixels, ColorSpaceConverter colorSpaceConverter, ChannelChooser channelChooser) {
        return channelChooser.apply(colorSpaceConverter.toRawData(concatenateRows(pixels)));
    }

    private List<RgbConvertable> concatenateRows(List<List<RgbConvertable>> pixels) {
        List<RgbConvertable> result = new ArrayList<>();

        for (List<RgbConvertable> row: pixels) {
            result.addAll(row);
        }

        return result;
    }

    private Header getHeaderForSave(Displayable displayable, ChannelChooser channelChooser) {
        return new Header(
                channelChooser.getMagicNumber(),
                displayable.getWidth(),
                displayable.getHeight(),
                255
        );
    }

    private byte[] getByteData(float[] input, ChannelChooser channelChooser) {
        if (channelChooser.getMagicNumber().equals("P6")) {
            return getFullByteData(input);
        }

        return getShrankByteData(input, channelChooser);
    }

    private byte[] getFullByteData(float[] input) {
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            result[i] = (byte) (input[i] * 255f);
        }

        return result;
    }

    private byte[] getShrankByteData(float[] input, ChannelChooser channelChooser) {
        byte[] result = new byte[input.length / 3];

        List<Integer> mask = channelChooser.getChannelMask();
        int valueSubIndex = mask.indexOf(1);

        for (int i = 0; i < result.length; i++) {
            byte data = (byte) (input[i * 3 + valueSubIndex] * 255f);
            result[i] = data;
        }

        return result;
    }
}
