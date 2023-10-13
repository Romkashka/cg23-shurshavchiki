package ru.shurshavchiki.businessLogic.services;

import lombok.Getter;
import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooserBuilder;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.factories.ColorSpaceFactory;
import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.colorSpace.util.ColorSpaceRegistry;
import ru.shurshavchiki.businessLogic.entities.Displayable;
import ru.shurshavchiki.businessLogic.entities.PnmFile;
import ru.shurshavchiki.businessLogic.exceptions.ChannelException;
import ru.shurshavchiki.businessLogic.exceptions.ColorSpaceException;
import ru.shurshavchiki.businessLogic.exceptions.OpenFileException;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;
import ru.shurshavchiki.businessLogic.gamma.util.GammaConvertersRegistry;
import ru.shurshavchiki.businessLogic.gamma.util.PlainGammaConvertersRegistry;
import ru.shurshavchiki.businessLogic.models.*;
import ru.shurshavchiki.businessLogic.util.PnmFileReader;
import ru.shurshavchiki.businessLogic.util.PnmFileWriter;
import ru.shurshavchiki.businessLogic.util.ProjectDataHolderImpl;
import ru.shurshavchiki.businessLogic.util.UserProjectDataHolder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FileService {
    @Getter
    private final ColorSpaceRegistry colorSpaceRegistry;
    @Getter
    private final GammaConvertersRegistry gammaConvertersRegistry;
    private ColorSpaceFactory colorSpaceFactory;
    @Getter
    private ChannelChooser channelChooser;
    @Getter
    private final UserProjectDataHolder dataHolder;

    public FileService() {
        colorSpaceRegistry = new ColorSpaceRegistry();
        gammaConvertersRegistry = new PlainGammaConvertersRegistry();
        this.dataHolder = new ProjectDataHolderImpl();
        dataHolder.setInputGammaConverter(gammaConvertersRegistry.getGammaConverter(0));
        dataHolder.setShownGammaConverter(gammaConvertersRegistry.getGammaConverter(0));
    }

    public Displayable readFile(File file) throws IOException {
        dataHolder.setInputGammaConverter(gammaConvertersRegistry.getGammaConverter(0));
        dataHolder.setShownGammaConverter(gammaConvertersRegistry.getGammaConverter(0));
        checkFileIsReadable(file);
        ImageDataHolder imageDataHolder = new PnmFileReader(file).getImageDataHolder();
        Displayable pnmFile = new PnmFile(imageDataHolder.getHeader(),
                splitToRows(imageDataHolder.getHeader(), convertToPixels(imageDataHolder.getData())));
        dataHolder.setFile(file);
        dataHolder.setStartingDisplayable(pnmFile);
        dataHolder.setStartingChannelChooser(dataHolder.getChannelChooser());
        dataHolder.setStartingColorSpaceConverter(dataHolder.getStartingColorSpaceConverter());

        render();
        return dataHolder.getDisplayable();
    }

    public void saveFile(Displayable displayable, File file) throws IOException {
        new PnmFileWriter().saveFromRawData(file, dataHolder.getDisplayable().getHeader(), getByteData(convertToRawData(dataHolder.getDisplayableWithFilters().getAllPixels())));
    }

    public void restore() {
        dataHolder.setDisplayable(dataHolder.getStartingDisplayable());
        dataHolder.setChannelChooser(dataHolder.getStartingChannelChooser());
        dataHolder.setColorSpaceConverter(dataHolder.getStartingColorSpaceConverter());
    }

    public List<String> getColorSpacesNames() {
        return colorSpaceRegistry.getFactories().stream().map(ColorSpaceFactory::getColorSpaceName).toList();
    }

    public Displayable getPreview(Displayable source) {
        return dataHolder.getDisplayable();
    }

    public void chooseChannel(List<String> channelNames) {
        ChannelChooserBuilder builder = colorSpaceFactory.getChannelChooserBuilder();
        for (String name: channelNames) {
            Channel channel = getChannelFromName(name);
            builder.withChannel(channel);
        }
        channelChooser = builder.build();
        System.out.println(channelChooser.getConstants());
        dataHolder.setChannelChooser(channelChooser);

        render();
    }

    public void chooseColorSpace(String colorSpaceName) {
        this.colorSpaceFactory = colorSpaceRegistry.getFactoryByName(colorSpaceName);
        dataHolder.setColorSpaceConverter(getColorSpaceConverter());
        System.out.println("Color space name: " + colorSpaceFactory.getColorSpaceName());
        chooseChannel(colorSpaceFactory.getColorSpace().Channels().stream().map(Enum::name).toList());

        render();
    }

    public void assignGamma(float gamma) {
        GammaConverter newGammaConverter = gammaConvertersRegistry.getGammaConverter(gamma);
        dataHolder.setInputGammaConverter(newGammaConverter);
        render();
    }

    public void convertGamma(float gamma) {
        GammaConverter newGammaConverter = gammaConvertersRegistry.getGammaConverter(gamma);
        dataHolder.setShownGammaConverter(newGammaConverter);
        dataHolder.setInputGammaConverter(newGammaConverter);
        render();
        dataHolder.setStartingDisplayable(dataHolder.getDisplayable());
    }

    public ColorSpaceConverter getColorSpaceConverter() {
        return colorSpaceFactory.getColorSpaceConverter();
    }

    private void render() {
        if (dataHolder.getStartingDisplayable() == null) {
            return;
        }

        Header header = dataHolder.getStartingDisplayable().getHeader();
        dataHolder.setDisplayableWithFilters(new PnmFile(header, splitToRows(header, convertToPixels(convertToRawData(dataHolder.getStartingDisplayable().getAllPixels())))));

        applyGamma();
    }

    private void applyGamma() {
        dataHolder.setDisplayable(dataHolder.getDisplayableWithFilters());
        applyToEachPixel(dataHolder.getInputGammaConverter()::useGamma, dataHolder.getDisplayable());
        applyToEachPixel(dataHolder.getShownGammaConverter()::correctGamma, dataHolder.getDisplayable());
    }

    private float[] convertToRawData(List<List<RgbConvertable>> pixels) {
        return channelChooser.apply(dataHolder.getColorSpaceConverter().toRawData(concatenateRows(pixels)));
    }

    private List<RgbConvertable> convertToPixels(float[] rawData) {
        return getColorSpaceConverter().toRgb(dataHolder.getChannelChooser().fillAllChannels(rawData));
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

    private List<RgbConvertable> concatenateRows(List<List<RgbConvertable>> pixels) {
        List<RgbConvertable> result = new ArrayList<>();

        for (List<RgbConvertable> row: pixels) {
            result.addAll(row);
        }

        return result;
    }

    private Channel getChannelFromName(String channelName) {
        for (Channel channel: Channel.values()) {
            if (channel.name().equals(channelName))
                return channel;
        }

        throw ChannelException.noSuchChannel(channelName);
    }

    private void checkFileIsReadable(File file) {
        if (!file.isFile()) {
            throw OpenFileException.notAFile(file.getName());
        }

        if (!file.canRead()) {
            throw OpenFileException.fileCantBeRead();
        }
    }

    private Displayable applyToEachPixel(Function<Float, Float> func, Displayable displayable) {
        System.out.println("Apply to each pixel: " + func.apply(0.1f) + " " + func.apply(0.5f) + " " + func.apply(0.9f));
        for (List<RgbConvertable> row: displayable.getAllPixels()) {
            row.replaceAll(pixel -> applyToPixel(pixel, func));
        }

        return displayable;
    }

    private RgbConvertable applyToPixel(RgbConvertable pixel, Function<Float, Float> func) {
        if (pixel instanceof RgbPixel rgbPixel) {
            return new RgbPixel(func.apply(rgbPixel.FloatRed()),
                    func.apply(rgbPixel.FloatGreen()),
                    func.apply(rgbPixel.FloatBlue()));
        }

        throw new UnsupportedOperationException("Only RgbPixel are supported(((");
    }

    private byte[] getByteData(float[] input) {
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            result[i] = (byte) (input[i] * 255f);
        }

        return result;
    }
}
