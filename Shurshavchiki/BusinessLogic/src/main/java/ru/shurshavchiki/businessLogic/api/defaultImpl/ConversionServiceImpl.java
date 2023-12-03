package ru.shurshavchiki.businessLogic.api.defaultImpl;

import lombok.NonNull;
import ru.shurshavchiki.businessLogic.api.Context;
import ru.shurshavchiki.businessLogic.api.ConversionService;
import ru.shurshavchiki.businessLogic.api.UserProjectDataHolder;

public class ConversionServiceImpl implements ConversionService {
    private final DataHolderUpdateWizard dataHolderUpdateWizard;

    public ConversionServiceImpl(DataHolderUpdateWizard dataHolderUpdateWizard) {
        this.dataHolderUpdateWizard = dataHolderUpdateWizard;
    }

    @Override
    public void applyColorFilters(@NonNull Context context) {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        if (dataHolder.isColorSpaceChanged()) {
            dataHolder.setStartingDisplayable(dataHolder.getShownDisplayable());
        }
        applyColorFilters(dataHolder);
    }

    @Override
    public void assignGamma(@NonNull Context context) {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
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

    private void applyColorFilters(UserProjectDataHolder dataHolder) {
        dataHolderUpdateWizard.updateDisplayableWithColorSpaceAndChannels(dataHolder);
    }

    private void applyGamma(@NonNull UserProjectDataHolder dataHolder) {
        dataHolderUpdateWizard.updateDisplayableWithLinearGamma(dataHolder);
    }

    private UserProjectDataHolder extractDataHolder(@NonNull Context context) {
        return context.getDataHolder();
    }
}
