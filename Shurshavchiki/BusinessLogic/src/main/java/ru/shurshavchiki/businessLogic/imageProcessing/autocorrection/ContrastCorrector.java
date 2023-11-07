package ru.shurshavchiki.businessLogic.imageProcessing.autocorrection;

import ru.shurshavchiki.businessLogic.colorSpace.models.SingleChannelUnit;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;

import java.util.List;

public interface ContrastCorrector {
    float calculateLowerLimit(List<Histogram> histograms);
    float calculateUpperLimit(List<Histogram> histograms);
    List<SingleChannelUnit> correctContrast(List<SingleChannelUnit> channelUnits, float lowerLimit, float upperLimit);
    SingleChannelUnit correctContrast(SingleChannelUnit channelUnit, float lowerLimit, float upperLimit);
}
