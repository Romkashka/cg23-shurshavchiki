 package ru.shurshavchiki.businessLogic.colorSpace.util;

import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.factories.ColorSpaceFactory;
import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;
import ru.shurshavchiki.businessLogic.colorSpace.models.SingleChannelUnit;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;

import java.util.ArrayList;
import java.util.List;

public class ChannelRearranger {
    public List<SingleChannelUnit> splitToChannels(List<RgbConvertable> pixels, ColorSpaceFactory colorSpaceFactory) {
        ColorSpaceConverter converter = colorSpaceFactory.getColorSpaceConverter();
        ColorSpace colorSpace = colorSpaceFactory.getColorSpace();
        float[] data = converter.toRawData(pixels);
        List<SingleChannelUnit> result = new ArrayList<>(3);
        for (Channel channel: colorSpace.Channels()) {
            int channelIndex = colorSpace.Channels().indexOf(channel);
            Float[] values = new Float[pixels.size()];
            for (int i = 0; i < pixels.size(); i++) {
                values[i] = data[i * 3 + channelIndex];
            }
            result.add(new SingleChannelUnit(channel, values));
        }

        return result;
    }

    public List<RgbConvertable> concatenateChannelUnits(List<SingleChannelUnit> channelUnits, ColorSpaceFactory colorSpaceFactory) {
        int channelsAmount = channelUnits.get(0).Values().length;
        float[] rawData = new float[channelUnits.size() * channelsAmount];

        for (int i = 0; i < channelUnits.size(); i++) {
            SingleChannelUnit unit = channelUnits.get(i);
            for (int j = 0; j < unit.Values().length; j++) {
                rawData[j * channelsAmount + i] = unit.Values()[j];
            }
        }

        return colorSpaceFactory.getColorSpaceConverter().toRgb(rawData);
    }
}
