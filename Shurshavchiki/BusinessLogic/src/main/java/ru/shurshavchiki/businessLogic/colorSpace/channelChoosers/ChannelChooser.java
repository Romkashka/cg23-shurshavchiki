package ru.shurshavchiki.businessLogic.colorSpace.channelChoosers;

import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;

import java.util.List;

public interface ChannelChooser {
    String getMagicNumber();
    float[] apply(float[] allChannelsData);
    float[] fillAllChannels(float[] rawData);
    List<Channel> getChannels();

    List<Float> getConstants();
    List<Integer> getChannelMask();
}
