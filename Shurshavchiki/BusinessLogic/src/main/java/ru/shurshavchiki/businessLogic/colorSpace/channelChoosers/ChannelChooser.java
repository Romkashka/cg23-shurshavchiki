package ru.shurshavchiki.businessLogic.colorSpace.channelChoosers;

import java.util.List;

public interface ChannelChooser {
    String getMagicNumber();
    float[] apply(float[] allChannelsData);
    float[] fillAllChannels(float[] rawData);

    List<Float> getConstants();
}
