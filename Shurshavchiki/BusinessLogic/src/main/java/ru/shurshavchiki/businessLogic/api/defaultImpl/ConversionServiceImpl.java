package ru.shurshavchiki.businessLogic.api.defaultImpl;

import lombok.NonNull;
import ru.shurshavchiki.businessLogic.api.Context;
import ru.shurshavchiki.businessLogic.api.ConversionService;
import ru.shurshavchiki.businessLogic.api.UserProjectDataHolder;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.services.FileService;

public class ConversionServiceImpl implements ConversionService {
    private final FileService fileService;

    public ConversionServiceImpl(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public void applyColorFilters(@NonNull Context context) {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        Displayable result = fileService.applyColorFilters(dataHolder.getStartingDisplayable(),
                dataHolder.getColorSpaceFactory().getColorSpaceConverter(),
                dataHolder.getChannelChooser());
        dataHolder.setDisplayableWithFilters(result);
        applyGamma(dataHolder);
    }

    @Override
    public void assignGamma(@NonNull Context context) {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
//        Displayable result = fileService.assignGamma(dataHolder.getShownDisplayable(), dataHolder.getNewGammaConverter());
//        dataHolder.setShownDisplayable(result);
        dataHolder.setInputGammaConverter(dataHolder.getNewGammaConverter());
        dataHolder.setNewGammaConverter(null);
        applyGamma(dataHolder);
    }

    @Override
    public void convertGamma(@NonNull Context context) {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        dataHolder.setInputGammaConverter(dataHolder.getNewGammaConverter());
        dataHolder.setShownGammaConverter(dataHolder.getNewGammaConverter());
        dataHolder.setNewGammaConverter(null);
        applyGamma(dataHolder);
    }

    private void applyGamma(@NonNull UserProjectDataHolder dataHolder) {
        dataHolder.setDisplayableWithLinearGamma(fileService.useGamma(dataHolder.getDisplayableWithFilters(),
                dataHolder.getInputGammaConverter()));
        dataHolder.setShownDisplayable(fileService.correctGamma(dataHolder.getDisplayableWithLinearGamma(),
                dataHolder.getShownGammaConverter()));
    }

    private UserProjectDataHolder extractDataHolder(@NonNull Context context) {
        return context.getDataHolder();
    }
}
