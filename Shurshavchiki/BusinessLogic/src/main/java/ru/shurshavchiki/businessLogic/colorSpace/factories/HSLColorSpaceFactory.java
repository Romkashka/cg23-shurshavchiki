package ru.shurshavchiki.businessLogic.colorSpace.factories;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooserBuilder;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.converters.PredicateBasedConverter;
import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HSLColorSpaceFactory implements ColorSpaceFactory {
    private static final String COLOR_SPACE_NAME = "HSL";
    @Override
    public String getColorSpaceName() {
        return COLOR_SPACE_NAME;
    }

    @Override
    public ColorSpace getColorSpace() {
        return new ColorSpace(COLOR_SPACE_NAME, List.of(Channel.HUE, Channel.SATURATION, Channel.LIGHTNESS));
    }

    @Override
    public ChannelChooserBuilder getChannelChooserBuilder() {
        return new ChannelChooserBuilder(getColorSpace(), List.of(0F, 255F, 127.5F));
    }

    @Override
    public ColorSpaceConverter getColorSpaceConverter() {
        return new PredicateBasedConverter(
                input -> {
                    float Cmax = Collections.max(Arrays.asList(input));
                    float Cmin = Collections.min(Arrays.asList(input));
                    float H;
                    if (Cmax == input[0] && input[1] >= input[2]) {
                        H = (input[1] - input[2]) / (Cmax - Cmin) * 255 / 6;
                    } else if (Cmax == input[0]) {
                        H = (input[1] - input[2]) / (Cmax - Cmin) * 255 / 6 + 256F;
                    } else if (Cmax == input[1]) {
                        H = (input[2] - input[0]) / (Cmax - Cmin) * 255 / 6 + 85.3F;
                    } else {
                        H = (input[0] - input[1]) / (Cmax - Cmin) * 255 / 6 + 170.67F;
                    }
                    float L = (Cmin + Cmax) / 2;
                    float S = (Cmax - Cmin) / (255 - Math.abs(255 - Cmax - Cmin)) * 255F;
                    return new Float[]{H, S, L};
                },
                input -> {
                    float H = input[0] / 255 * 360;
                    float C = (1F - Math.abs(2 * input[2] / 255F - 1F)) * input[1] / 255F;
                    float X = C * (1 - Math.abs(H / 60 % 2 -1));
                    float m = input[2] / 255F - C / 2;
                    float[] tmp;
                    if (H < 60F) tmp = new float[]{C, X, 0F};
                    else if (H < 120F) tmp = new float[]{X, C, 0F};
                    else if (H < 180F) tmp = new float[]{0F, C, X};
                    else if (H < 240F) tmp = new float[]{0F, X, C};
                    else if (H < 300F) tmp = new float[]{X, 0F, C};
                    else tmp = new float[]{C, 0F, X};
                    return new Float[]{(tmp[0] + m) * 255F, (tmp[1] + m) * 255, (tmp[2] + m) * 255};
                }
        );
    }
}
