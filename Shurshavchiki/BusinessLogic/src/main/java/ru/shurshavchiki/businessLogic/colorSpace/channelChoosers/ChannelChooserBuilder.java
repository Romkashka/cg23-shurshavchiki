package ru.shurshavchiki.businessLogic.colorSpace.channelChoosers;

import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;
import ru.shurshavchiki.businessLogic.exceptions.ChannelException;

import java.util.ArrayList;
import java.util.List;

public class ChannelChooserBuilder {
    private ColorSpace colorSpace;
    private List<Double> constants;
    private List<Integer> channelMask;

    public ChannelChooserBuilder(ColorSpace colorSpace, List<Double> constants) {
        this.colorSpace = colorSpace;
        this.constants = constants;
        channelMask = List.of(1, 1, 1);
    }

    public ChannelChooserBuilder withChannel(Channel channel) {
        if (channel.equals(Channel.ALL)) {
            return withAllChannels();
        }

        if (!colorSpace.Channels().contains(channel)) {
            throw ChannelException.noSuchChannel(colorSpace.Name(), channel);
        }

        int index = colorSpace.Channels().indexOf(channel);
        channelMask = new ArrayList<>(List.of(0, 0, 0));
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
