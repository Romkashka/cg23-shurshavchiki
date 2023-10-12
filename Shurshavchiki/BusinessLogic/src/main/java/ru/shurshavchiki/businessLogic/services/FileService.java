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
    private GammaConverter gammaConverter;

    @Getter
    private UserProjectDataHolder dataHolder;

    public FileService() {
        colorSpaceRegistry = new ColorSpaceRegistry();
        gammaConvertersRegistry = new PlainGammaConvertersRegistry();
        this.dataHolder = new ProjectDataHolderImpl();
    }

    public Displayable readFile(File file) throws IOException {
        checkFileIsReadable(file);
        ImageDataHolder imageDataHolder = new PnmFileReader(file).getImageDataHolder();
        Displayable pnmFile = new PnmFile(imageDataHolder.getHeader(),
                splitToRows(imageDataHolder.getHeader(), convertToPixels(imageDataHolder.getData())));
        dataHolder.setFile(file);
        dataHolder.setDisplayable(pnmFile);

        return pnmFile;
    }

    public void saveFile(Displayable displayable, File file) throws IOException {
        new PnmFileWriter().save(displayable, file);
    }

    public List<String> getColorSpacesNames() {
        return colorSpaceRegistry.getFactories().stream().map(ColorSpaceFactory::getColorSpaceName).toList();
    }

    public Displayable getPreview(Displayable source) {
        Header header = source.getHeader();
        return new PnmFile(header,
                splitToRows(header, convertToPixels(convertToRawData(source.getAllPixels()))));
    }

    public void chooseChannel(List<String> channelNames) {
        ChannelChooserBuilder builder = colorSpaceFactory.getChannelChooserBuilder();
        for (String name: channelNames) {
            Channel channel = getChannelFromName(name);
            builder.withChannel(channel);
        }
        channelChooser = builder.build();
        dataHolder.setChannelChooser(channelChooser);
    }

    public void chooseColorSpace(String colorSpaceName) {
        this.colorSpaceFactory = colorSpaceRegistry.getFactoryByName(colorSpaceName);
        dataHolder.setColorSpaceConverter(getColorSpaceConverter());
    }

    public void assignGamma(float gamma) {
        GammaConverter newGammaConverter = gammaConvertersRegistry.getGammaConverter(gamma);
        applyToEachPixel(newGammaConverter::useGamma);
        applyToEachPixel(dataHolder.getGammaConverter()::correctGamma);
        dataHolder.setGammaConverter(newGammaConverter);
    }

    public void convertGamma(float gamma) {
        GammaConverter newGammaConverter = gammaConvertersRegistry.getGammaConverter(gamma);
        applyToEachPixel(dataHolder.getGammaConverter()::useGamma);
        applyToEachPixel(newGammaConverter::correctGamma);
        dataHolder.setGammaConverter(newGammaConverter);
    }

    public ColorSpaceConverter getColorSpaceConverter() {
        return colorSpaceFactory.getColorSpaceConverter();
    }

    private float[] convertToRawData(List<List<RgbConvertable>> pixels) {
        return channelChooser.apply(getColorSpaceConverter().toRawData(concatenateRows(pixels)));
    }

    private List<RgbConvertable> convertToPixels(float[] rawData) {
        return getColorSpaceConverter().toRgb(channelChooser.fillAllChannels(rawData));
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

    private void applyToEachPixel(Function<Float, Float> func) {
        for (List<RgbConvertable> row: dataHolder.getDisplayable().getAllPixels()) {
            row.replaceAll(pixel -> applyToPixel(pixel, func));
        }
    }

    private RgbConvertable applyToPixel(RgbConvertable pixel, Function<Float, Float> func) {
        if (pixel instanceof RgbPixel rgbPixel) {
            return new RgbPixel(func.apply(rgbPixel.FloatRed()),
                    func.apply(rgbPixel.FloatGreen()),
                    func.apply(rgbPixel.FloatBlue()));
        }
        else if (pixel instanceof MonochromePixel monochromePixel) {
            return new MonochromePixel(func.apply(monochromePixel.FloatRed()));
        }

        throw new UnsupportedOperationException("Only RgbPixel and MonochromePixel are supported(((");
    }
}
