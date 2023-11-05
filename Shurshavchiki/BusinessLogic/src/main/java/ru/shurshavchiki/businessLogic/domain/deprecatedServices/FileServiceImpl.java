package ru.shurshavchiki.businessLogic.domain.deprecatedServices;

import lombok.Getter;
import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.factories.ColorSpaceFactory;
import ru.shurshavchiki.businessLogic.colorSpace.util.ColorSpaceRegistry;
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
import ru.shurshavchiki.businessLogic.gamma.util.GammaConvertersRegistry;
import ru.shurshavchiki.businessLogic.gamma.util.PlainGammaConvertersRegistry;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class FileServiceImpl implements FileService {
    @Getter
    private final ColorSpaceRegistry colorSpaceRegistry;
    @Getter
    private final GammaConvertersRegistry gammaConvertersRegistry;
    private ColorSpaceFactory colorSpaceFactory;
    @Getter
    private ChannelChooser channelChooser;
    @Getter
    private final UserProjectDataHolder dataHolder;

    public FileServiceImpl(UserProjectDataHolder dataHolder) {
        colorSpaceRegistry = new ColorSpaceRegistry();
        gammaConvertersRegistry = new PlainGammaConvertersRegistry();
        this.dataHolder = dataHolder;
        dataHolder.setInputGammaConverter(gammaConvertersRegistry.getGammaConverter(0));
        dataHolder.setShownGammaConverter(gammaConvertersRegistry.getGammaConverter(0));
    }

    public Displayable readFile(File file) throws IOException {
        checkFileIsReadable(file);
        ImageDataHolder imageDataHolder = new PnmFileReader(file).getImageDataHolder();
        Displayable pnmFile = new PnmFile(imageDataHolder.getHeader(),
                splitToRows(imageDataHolder.getHeader(), convertToPixels(imageDataHolder.getData())));
        dataHolder.setStartingDisplayable(pnmFile);

        render();
        return dataHolder.getShownDisplayable();
    }

    public void saveFile(Displayable displayable, File file) throws IOException {
        if (file == null) {
            throw WriteFileException.noFile();
        }

        new PnmFileWriter().saveFromRawData(file,
                getHeaderForSave(),
                getByteData(convertToRawData(displayable.getAllPixels())));
    }

    public List<String> getColorSpacesNames() {
        return colorSpaceRegistry.getFactories().stream().map(ColorSpaceFactory::getName).toList();
    }

    public Displayable chooseChannel(Displayable source, ChannelChooser channelChooser) {
//        ChannelChooserBuilder builder = colorSpaceFactory.getChannelChooserBuilder();
//        for (String name: channelNames) {
//            Channel channel = getChannelFromName(name);
//            builder.withChannel(channel);
//        }
//        channelChooser = builder.build();
//        System.out.println(channelChooser.getConstants());
        dataHolder.setChannelChooser(channelChooser);

        render();
        return null;
    }

    @Override
    public Displayable chooseColorSpace(Displayable source, ColorSpaceConverter colorSpaceConverter) {
        return null;
    }

    @Override
    public Displayable assignGamma(Displayable source, GammaConverter gammaConverter) {
        return null;
    }

    @Override
    public Displayable convertGamma(Displayable source, GammaConverter gammaConverter) {
        return null;
    }

    public void chooseColorSpace(String colorSpaceName) {
        this.colorSpaceFactory = colorSpaceRegistry.getFactoryByName(colorSpaceName);
        dataHolder.setColorSpaceConverter(getColorSpaceConverter());
        System.out.println("Color space name: " + colorSpaceFactory.getName());
//        chooseChannel(colorSpaceFactory.getColorSpace().Channels().stream().map(Enum::name).toList());

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
        dataHolder.setStartingDisplayable(dataHolder.getShownDisplayable());
    }

    public ColorSpaceConverter getColorSpaceConverter() {
        return colorSpaceFactory.getColorSpaceConverter();
    }

    public void render() {
        if (dataHolder.getStartingDisplayable() == null) {
            return;
        }

        Header header = dataHolder.getStartingDisplayable().getHeader();
        dataHolder.setDisplayableWithFilters(new PnmFile(header, splitToRows(header, convertToPixels(convertToRawData(dataHolder.getStartingDisplayable().getAllPixels())))));

        applyGamma();
    }

    private void applyGamma() {
        dataHolder.setShownDisplayable(dataHolder.getDisplayableWithFilters());
        applyToEachPixel(dataHolder.getInputGammaConverter()::useGamma, dataHolder.getShownDisplayable());
        applyToEachPixel(dataHolder.getShownGammaConverter()::correctGamma, dataHolder.getShownDisplayable());
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



    private void checkFileIsReadable(File file) {
        if (!file.isFile()) {
            throw OpenFileException.notAFile(file.getName());
        }

        if (!file.canRead()) {
            throw OpenFileException.fileCantBeRead();
        }
    }

    private void applyToEachPixel(Function<Float, Float> func, Displayable displayable) {
        System.out.println("Apply to each pixel: " + func.apply(0.1f) + " " + func.apply(0.5f) + " " + func.apply(0.9f));
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

    private Header getHeaderForSave() {
        return new Header(
                dataHolder.getChannelChooser().getMagicNumber(),
                getDisplayableToSave().getWidth(),
                getDisplayableToSave().getHeight(),
                255
        );
    }

    private Displayable getDisplayableToSave() {
        return dataHolder.getDisplayableWithFilters();
    }

    private byte[] getByteData(float[] input) {
        if (dataHolder.getChannelChooser().getMagicNumber().equals("P6")) {
            return getFullByteData(input);
        }

        return getShrankByteData(input);
    }

    private byte[] getFullByteData(float[] input) {
        byte[] result = new byte[input.length];

        for (int i = 0; i < input.length; i++) {
            result[i] = (byte) (input[i] * 255f);
        }

        return result;
    }

    private byte[] getShrankByteData(float[] input) {
        byte[] result = new byte[input.length / 3];

        System.out.println("input size: " + input.length);
        System.out.println("result size: " + result.length);

        List<Integer> mask = dataHolder.getChannelChooser().getChannelMask();
        int valueSubIndex = mask.indexOf(1);

        System.out.println(valueSubIndex);

        for (int i = 0; i < result.length; i++) {
            byte data = (byte) (input[i * 3 + valueSubIndex] * 255f);
            result[i] = data;
        }

        return result;
    }
}
