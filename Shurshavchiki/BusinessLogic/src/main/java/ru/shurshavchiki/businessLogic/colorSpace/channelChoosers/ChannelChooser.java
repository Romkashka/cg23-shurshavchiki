package ru.shurshavchiki.businessLogic.colorSpace.channelChoosers;

public interface ChannelChooser {
    String getMagicNumber();
    byte[] apply(byte[] allChannelsData);
    byte[] fillAllChannels(byte[] rawData);
}
