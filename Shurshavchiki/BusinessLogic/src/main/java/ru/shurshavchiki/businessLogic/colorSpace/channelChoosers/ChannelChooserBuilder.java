package ru.shurshavchiki.businessLogic.colorSpace.channelChoosers;

import lombok.Getter;
import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;
import ru.shurshavchiki.businessLogic.exceptions.ChannelException;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChannelChooserBuilder {
    private ColorSpace colorSpace;
    @Getter
    private List<Float> constants;
    private List<Integer> channelMask;

    public ChannelChooserBuilder(ColorSpace colorSpace, List<Float> constants) {
        this.colorSpace = colorSpace;
        this.constants = constants;
        channelMask = new ArrayList<>(List.of(0, 0, 0));
        System.out.println(channelMask.size());
    }

    public ChannelChooserBuilder withChannel(Channel channel) {
        if (!colorSpace.Channels().contains(channel)) {
            throw ChannelException.noSuchChannelInColorSpace(colorSpace.Name(), channel);
        }

        int index = colorSpace.Channels().indexOf(channel);
        channelMask.set(index, 1);
        return this;
    }

    public ChannelChooserBuilder withAllChannels() {
        channelMask = List.of(1, 1, 1);
        return this;
    }

    public ChannelChooser build() {
        return new SimpleChannelChooser(constants, channelMask);
    }
}
