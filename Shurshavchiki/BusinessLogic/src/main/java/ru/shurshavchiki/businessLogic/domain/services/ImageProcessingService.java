package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.filling.ImageCreationAlgorithm;

public interface ImageProcessingService {
    Displayable ditherImage(DitheringAlgorithm algorithm, Displayable source);
    Displayable createNewImage(ImageCreationAlgorithm algorithm, int height, int width);
}
