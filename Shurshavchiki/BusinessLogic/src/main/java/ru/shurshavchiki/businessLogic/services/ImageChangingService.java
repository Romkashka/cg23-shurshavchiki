package ru.shurshavchiki.businessLogic.services;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.factories.ColorSpaceFactory;
import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;
import ru.shurshavchiki.businessLogic.colorSpace.util.ColorSpaceRegistry;
import ru.shurshavchiki.businessLogic.entities.Displayable;
import ru.shurshavchiki.businessLogic.entities.PnmFile;
import ru.shurshavchiki.businessLogic.exceptions.ColorSpaceException;
import ru.shurshavchiki.businessLogic.models.Header;
import ru.shurshavchiki.businessLogic.models.RgbConvertable;

import java.util.ArrayList;
import java.util.List;

public class ImageChangingService {
    @Getter
    private final ColorSpaceRegistry colorSpaceRegistry;
    @Setter
    private ChannelChooser channelChooser;
    @Setter
    private ColorSpaceConverter colorSpaceConverter;

    public ImageChangingService(ColorSpaceRegistry colorSpaceRegistry) {
        this.colorSpaceRegistry = colorSpaceRegistry;
    }

    public List<String> getColorSpacesNames() {
        return colorSpaceRegistry.getFactories().stream().map(ColorSpaceFactory::getColorSpaceName).toList();
    }

    public Displayable getPreview(Displayable source) {
        Header header = new Header(source.getVersion(),
                source.getWidth(),
                source.getHeight(),
                255);
        return new PnmFile(header,
                splitToRows(header, convertToPixels(convertToRawData(source.getAllPixels()))));
    }

    private byte[] convertToRawData(List<List<RgbConvertable>> pixels) {
        return channelChooser.apply(colorSpaceConverter.toRawData(pixels));
    }

    private List<RgbConvertable> convertToPixels(byte[] rawData) {
        return colorSpaceConverter.toRgb(channelChooser.fillAllChannels(rawData));
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
}
