package ru.shurshavchiki.businessLogic.api.defaultImpl;

import lombok.NonNull;
import ru.shurshavchiki.businessLogic.api.Context;
import ru.shurshavchiki.businessLogic.api.ImageProcessingService;
import ru.shurshavchiki.businessLogic.api.UserProjectDataHolder;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithm;

public class ImageProcessingServiceImpl implements ImageProcessingService {
    private final ru.shurshavchiki.businessLogic.domain.services.ImageProcessingService imageProcessingService;

    public ImageProcessingServiceImpl(ru.shurshavchiki.businessLogic.domain.services.ImageProcessingService imageProcessingService) {
        this.imageProcessingService = imageProcessingService;
    }

    @Override
    public void ditherImage(@NonNull Context source, @NonNull Context destination) {
        UserProjectDataHolder sourceDataHolder = extractDataHolder(source);
        UserProjectDataHolder destinationDataHolder = extractDataHolder(destination);
        Displayable result = imageProcessingService.ditherImage(sourceDataHolder.getDitheringAlgorithm(), sourceDataHolder.getShownDisplayable());
        destinationDataHolder.setShownDisplayable(result);
    }

    private UserProjectDataHolder extractDataHolder(Context context) {
        return context.getDataHolder();
    }}
