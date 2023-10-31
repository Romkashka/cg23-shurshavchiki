package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.filling.ImageCreationAlgorithm;

public class ImageProcessingServiceImpl implements ImageProcessingService {
    @Override
    public Displayable ditherImage(DitheringAlgorithm algorithm, Displayable source) {
        return algorithm.applyDithering(source);
    }

    @Override
    public Displayable createNewImage(ImageCreationAlgorithm algorithm, int height, int width) {
        return algorithm.create(height, width);
    }
}
