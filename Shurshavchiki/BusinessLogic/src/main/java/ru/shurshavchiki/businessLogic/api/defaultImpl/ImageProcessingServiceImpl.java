package ru.shurshavchiki.businessLogic.api.defaultImpl;

import lombok.NonNull;
import ru.shurshavchiki.businessLogic.api.Context;
import ru.shurshavchiki.businessLogic.api.ImageProcessingService;
import ru.shurshavchiki.businessLogic.api.ServiceFactoryImpl;
import ru.shurshavchiki.businessLogic.api.UserProjectDataHolder;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithm;

public class ImageProcessingServiceImpl implements ImageProcessingService {
    private final ru.shurshavchiki.businessLogic.domain.services.ImageProcessingService imageProcessingService;

    public ImageProcessingServiceImpl(ru.shurshavchiki.businessLogic.domain.services.ImageProcessingService imageProcessingService) {
        this.imageProcessingService = imageProcessingService;
    }

    @Override
    public Context ditherImage(@NonNull Context source) {
        UserProjectDataHolder sourceDataHolder = extractDataHolder(source);
        Context resultContext = new ServiceFactoryImpl().getBlankContext();
        UserProjectDataHolder destinationDataHolder = resultContext.getDataHolder();
        Displayable result = imageProcessingService.ditherImage(sourceDataHolder.getDitheringAlgorithm(), sourceDataHolder.getShownDisplayable());
        destinationDataHolder.setShownDisplayable(result);
        return resultContext;
    }

    private UserProjectDataHolder extractDataHolder(Context context) {
        return context.getDataHolder();
    }}
