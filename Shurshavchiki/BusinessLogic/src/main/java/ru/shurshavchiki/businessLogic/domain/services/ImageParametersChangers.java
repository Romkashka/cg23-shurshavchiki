package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;

public record ImageParametersChangers(ColorSpaceConverter ColorSpaceConverter, ChannelChooser ChannelChooser, GammaConverter GammaConverter) {
}
