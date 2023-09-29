package ru.shurshavchiki.businessLogic.colorSpace.channelChoosers;

public interface ChannelChooser {
    String getMagicNumber();
    float[] apply(float[] allChannelsData);
    float[] fillAllChannels(float[] rawData);
}
