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
import ru.shurshavchiki.businessLogic.models.Header;
import ru.shurshavchiki.businessLogic.models.ImageDataHolder;
import ru.shurshavchiki.businessLogic.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.util.PnmFileReader;
import ru.shurshavchiki.businessLogic.util.PnmFileWriter;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileService {
    @Getter
    private final ColorSpaceRegistry colorSpaceRegistry;
    private ColorSpaceFactory colorSpaceFactory;
    @Getter
    private ChannelChooser channelChooser;

    public FileService() {
        colorSpaceRegistry = new ColorSpaceRegistry();
    }

    public Displayable readFile(File file) throws IOException {
        checkFileIsReadable(file);

        ImageDataHolder dataHolder = new PnmFileReader(file).getImageDataHolder(file);

        return new PnmFile(dataHolder.getHeader(),
                splitToRows(dataHolder.getHeader(), convertToPixels(dataHolder.getData())));
    }

    public void saveFile(Displayable displayable, File file) throws IOException {
        new PnmFileWriter().save(displayable, file);
    }

    public void saveFromRawData(File file, Header header, byte[] data) throws IOException {
        new PnmFileWriter().saveFromRawData(file, header, data);
    }

    public List<String> getColorSpacesNames() {
        return colorSpaceRegistry.getFactories().stream().map(ColorSpaceFactory::getColorSpaceName).toList();
    }

    public Channel getChannelFromName(String channelName) {
        for (Channel channel: Channel.values()) {
            if (channel.name().equals(channelName))
                return channel;
        }

        throw ChannelException.noSuchChannel(channelName);
    }

    public Displayable getPreview(Displayable source) {
        Header header = source.getHeader();
        return new PnmFile(header,
                splitToRows(header, convertToPixels(convertToRawData(source.getAllPixels()))));
    }

    public void chooseChannel(String channelName) {
        Channel channel = getChannelFromName(channelName);
        ChannelChooserBuilder builder = colorSpaceFactory.getChannelChooserBuilder();
        builder.withChannel(channel);
        channelChooser = builder.build();
    }

    public void chooseColorSpace(String colorSpaceName) {
        this.colorSpaceFactory = colorSpaceRegistry.getFactoryByName(colorSpaceName);
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

    private void checkFileIsReadable(File file) {
        if (!file.isFile()) {
            throw OpenFileException.notAFile(file.getName());
        }

        if (!file.canRead()) {
            throw OpenFileException.fileCantBeRead();
        }
    }
}
