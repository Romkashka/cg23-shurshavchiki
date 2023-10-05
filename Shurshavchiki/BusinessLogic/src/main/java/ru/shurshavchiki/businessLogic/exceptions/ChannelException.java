package ru.shurshavchiki.businessLogic.exceptions;

import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;

public class ChannelException extends ColorSpaceException {
    protected ChannelException(String message) {
        super(message);
    }

    public static ChannelException invalidChannelMask() {
        return new ChannelException("Either 1 or all channels can be chosen");
    }

    public static ChannelException noSuchChannelInColorSpace(String colorSpaceName, Channel channel) {
        return new ChannelException("Color space '" + colorSpaceName + "' doesn't contain channel " + channel.toString());
    }

    public static ChannelException noSuchChannel(String name) {
        return new ChannelException("There is no channel with name " + name);
    }
}
