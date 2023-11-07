package ru.shurshavchiki.businessLogic.api.defaultImpl;

import lombok.NonNull;
import ru.shurshavchiki.businessLogic.api.Context;
import ru.shurshavchiki.businessLogic.api.ImageProcessingService;
import ru.shurshavchiki.businessLogic.api.ServiceFactoryImpl;
import ru.shurshavchiki.businessLogic.api.UserProjectDataHolder;
import ru.shurshavchiki.businessLogic.colorSpace.models.SingleChannelUnit;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.services.FileService;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.Histogram;

import java.util.List;

public class ImageProcessingServiceImpl implements ImageProcessingService {
    private final ru.shurshavchiki.businessLogic.domain.services.ImageProcessingService imageProcessingService;
    private final FileService fileService;

    public ImageProcessingServiceImpl(ru.shurshavchiki.businessLogic.domain.services.ImageProcessingService imageProcessingService, FileService fileService) {
        this.imageProcessingService = imageProcessingService;
        this.fileService = fileService;
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

    @Override
    public void generateHistograms(@NonNull Context context) {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        List<SingleChannelUnit> channelUnitList = fileService.splitToChannels(dataHolder.getShownDisplayable(), dataHolder.getColorSpaceFactory());
        List<Histogram> histograms = imageProcessingService.generateHistograms(channelUnitList, dataHolder.getColorSpaceFactory().getColorSpace());
        dataHolder.setHistograms(histograms);
    }

    @Override
    public void autocorrectImage(@NonNull Context context) {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        List<SingleChannelUnit> channelUnits = fileService.splitToChannels(dataHolder.getShownDisplayable(), dataHolder.getColorSpaceFactory());
        channelUnits = imageProcessingService.autocorrectImage(channelUnits,
                dataHolder.getColorSpaceFactory().getColorSpace(),
                dataHolder.getHistograms(),
                dataHolder.getContrastCorrector());
        Displayable result = fileService.concatenateChannelUnits(dataHolder.getShownDisplayable().getHeader(), channelUnits, dataHolder.getColorSpaceFactory());
        dataHolder.setShownDisplayable(result);
    }

    private UserProjectDataHolder extractDataHolder(Context context) {
        return context.getDataHolder();
    }}
