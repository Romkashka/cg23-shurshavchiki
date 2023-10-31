package ru.shurshavchiki.businessLogic.domain.deprecatedServices;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.filling.ImageCreationAlgorithm;

public interface ImageProcessingService {
    Displayable createNewImage(ImageCreationAlgorithm algorithm, int height, int width);
    Displayable ditherImage(DitheringAlgorithm algorithm, Displayable source);
}
