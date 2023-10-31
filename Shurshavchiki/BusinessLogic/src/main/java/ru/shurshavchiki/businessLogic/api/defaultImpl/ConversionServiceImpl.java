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
        Displayable result = fileService.applyColorFilters(dataHolder.getShownDisplayable(),
                dataHolder.getColorSpaceFactory().getColorSpaceConverter(),
                dataHolder.getChannelChooser());
        dataHolder.setShownDisplayable(result);
        dataHolder.setDisplayableWithFilters(result);
    }

    @Override
    public void assignGamma(@NonNull Context context) {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        Displayable result = fileService.assignGamma(dataHolder.getShownDisplayable(), dataHolder.getNewGammaConverter());
        dataHolder.setShownDisplayable(result);
        dataHolder.setInputGammaConverter(dataHolder.getNewGammaConverter());
        dataHolder.setNewGammaConverter(null);
    }

    @Override
    public void convertGamma(@NonNull Context context) {
        throw new UnsupportedOperationException("I will implement it eventually");
    }

    private UserProjectDataHolder extractDataHolder(@NonNull Context context) {
        return context.getDataHolder();
    }
}
