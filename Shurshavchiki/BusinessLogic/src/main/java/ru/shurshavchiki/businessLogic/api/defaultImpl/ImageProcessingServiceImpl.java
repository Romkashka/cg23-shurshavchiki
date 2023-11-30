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
    private final DataHolderUpdateWizard dataHolderUpdateWizard;

    public ImageProcessingServiceImpl(ru.shurshavchiki.businessLogic.domain.services.ImageProcessingService imageProcessingService, FileService fileService, DataHolderUpdateWizard dataHolderUpdateWizard) {
        this.imageProcessingService = imageProcessingService;
        this.fileService = fileService;
        this.dataHolderUpdateWizard = dataHolderUpdateWizard;
    }

    @Override
    public Context ditherImage(@NonNull Context source) {
        UserProjectDataHolder sourceDataHolder = extractDataHolder(source);
        Context resultContext = new ServiceFactoryImpl().getBlankContext();
        UserProjectDataHolder destinationDataHolder = resultContext.getDataHolder();
        sourceDataHolder.setStartingDisplayable(sourceDataHolder.getShownDisplayable());
        dataHolderUpdateWizard.forceUpdateDataHolder(sourceDataHolder);
        if (sourceDataHolder.getDitheringAlgorithm().isInLineGamma()) {
            Displayable result = imageProcessingService.ditherImage(sourceDataHolder.getDitheringAlgorithm(), sourceDataHolder.getDisplayableWithLinearGamma());
            destinationDataHolder.setDisplayableWithLinearGamma(result);
            destinationDataHolder.setShownGammaConverter(sourceDataHolder.getShownGammaConverter());
            destinationDataHolder.setInputGammaConverter(sourceDataHolder.getInputGammaConverter());
            dataHolderUpdateWizard.updateDisplayableWithDrawings(destinationDataHolder);
        }
        else {
            Displayable result = imageProcessingService.ditherImage(sourceDataHolder.getDitheringAlgorithm(), sourceDataHolder.getShownDisplayable());
            destinationDataHolder.setShownDisplayable(result);
        }
        return resultContext;
    }

    @Override
    public void generateHistograms(@NonNull Context context) {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        List<SingleChannelUnit> channelUnitList = fileService.splitToChannels(dataHolder.getShownDisplayable(), dataHolder.getColorSpaceFactory());
        List<Histogram> histograms = imageProcessingService.generateHistograms(channelUnitList, dataHolder.getColorSpaceFactory().getColorSpace(), dataHolder.getChannelChooser());
        dataHolder.setHistograms(histograms);
    }

    @Override
    public void autocorrectImage(@NonNull Context context) {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        List<SingleChannelUnit> channelUnits = fileService.splitToChannels(dataHolder.getDisplayableWithDrawings(), dataHolder.getColorSpaceFactory());
        channelUnits = imageProcessingService.autocorrectImage(channelUnits,
                dataHolder.getColorSpaceFactory().getColorSpace(),
                dataHolder.getHistograms(),
                dataHolder.getContrastCorrector());
        Displayable result = fileService.concatenateChannelUnits(dataHolder.getDisplayableWithDrawings().getHeader(), channelUnits, dataHolder.getColorSpaceFactory());
        dataHolder.setDisplayableWithDrawings(result);
        dataHolderUpdateWizard.updateDisplayableWithDrawings(dataHolder);
        dataHolder.setStartingDisplayable(dataHolder.getShownDisplayable());
        dataHolderUpdateWizard.forceUpdateDataHolder(dataHolder);
    }

    @Override
    public void scaleImage(@NonNull Context context) {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        dataHolder.setStartingDisplayable(dataHolder.getShownDisplayable());
        dataHolderUpdateWizard.forceUpdateDataHolder(dataHolder);
        Displayable result = imageProcessingService.scaleImage(dataHolder.getDisplayableWithLinearGamma(), dataHolder.getScalingAlgorithm(), dataHolder.getScalingParameters());
        dataHolder.setDisplayableWithLinearGamma(result);
        dataHolderUpdateWizard.updateDisplayableWithDrawings(dataHolder);
        dataHolder.setStartingDisplayable(dataHolder.getShownDisplayable());
        dataHolderUpdateWizard.forceUpdateDataHolder(dataHolder);
    }

    private UserProjectDataHolder extractDataHolder(Context context) {
        return context.getDataHolder();
    }}
