package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;
import ru.shurshavchiki.businessLogic.colorSpace.models.SingleChannelUnit;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.ContrastCorrector;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.Histogram;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.filling.ImageCreationAlgorithm;

import java.util.List;

public interface ImageProcessingService {
    Displayable ditherImage(DitheringAlgorithm algorithm, Displayable source);
    Displayable createNewImage(ImageCreationAlgorithm algorithm, int height, int width);
    List<Histogram> generateHistograms(List<SingleChannelUnit> singleChannelUnits, ColorSpace colorSpace);
    List<SingleChannelUnit> autocorrectImage(List<SingleChannelUnit> channelUnits, ColorSpace colorSpace, List<Histogram> distributions, ContrastCorrector contrastCorrector);
}
