package ru.shurshavchiki.businessLogic.api.defaultImpl;

import lombok.AllArgsConstructor;
import ru.shurshavchiki.businessLogic.domain.services.ImageProcessingService;
import ru.shurshavchiki.businessLogic.api.UserProjectDataHolder;
import ru.shurshavchiki.businessLogic.domain.services.DrawingService;
import ru.shurshavchiki.businessLogic.domain.services.FileService;
import ru.shurshavchiki.businessLogic.exceptions.DataHolderUpdateException;

@AllArgsConstructor
public class DataHolderUpdateWizard {
    private final FileService fileService;
    private final DrawingService drawingService;
    private final ImageProcessingService imageProcessingService;

    public void forceUpdateDataHolder(UserProjectDataHolder dataHolder) {
        discardAllImages(dataHolder);

        updateShownDisplayable(dataHolder);
    }

    public void updateDisplayableWithColorSpaceAndChannels(UserProjectDataHolder dataHolder) {
        if (dataHolder.getStartingDisplayable() == null) {
            throw DataHolderUpdateException.NoImageToUpdate();
        }

        dataHolder.setDisplayableWithColorSpaceAndChannels(fileService.applyColorFilters(dataHolder.getStartingDisplayable(),
                dataHolder.getColorSpaceFactory().getColorSpaceConverter(),
                dataHolder.getChannelChooser()));
        dataHolder.setColorSpaceChanged(false);

        updateDisplayableWithLinearGamma(dataHolder);
    }

    public void updateDisplayableWithLinearGamma(UserProjectDataHolder dataHolder) {
        if (dataHolder.getDisplayableWithColorSpaceAndChannels() == null) {
            updateDisplayableWithColorSpaceAndChannels(dataHolder);
        }

        dataHolder.setDisplayableWithLinearGamma(
                fileService.useGamma(dataHolder.getDisplayableWithColorSpaceAndChannels(), dataHolder.getInputGammaConverter()));

        updateDisplayableWithDrawings(dataHolder);
    }

    public void updateDisplayableWithDrawings(UserProjectDataHolder dataHolder) {
        if (dataHolder.getDisplayableWithLinearGamma() == null) {
            updateDisplayableWithLinearGamma(dataHolder);
        }

        if (dataHolder.getDrawings().size() == 0) {
            dataHolder.setDisplayableWithDrawings(dataHolder.getDisplayableWithLinearGamma());
            updateShownDisplayable(dataHolder);
            return;
        }

        dataHolder.setDisplayableWithDrawings(
                drawingService.mergeDrawings(dataHolder.getDisplayableWithDrawings(), dataHolder.getDrawings()));

        dataHolder.deleteDrawing(0);
        updateDisplayableWithFilters(dataHolder);
    }

    public void updateDisplayableWithFilters(UserProjectDataHolder dataHolder) {
        if (dataHolder.getDisplayableWithDrawings() == null) {
            updateDisplayableWithDrawings(dataHolder);
        }

        if (dataHolder.getImageFilter() == null) {
            dataHolder.setDisplayableWithFilters(dataHolder.getDisplayableWithDrawings());
        }

        updateShownDisplayable(dataHolder);
    }

    public void updateShownDisplayable(UserProjectDataHolder dataHolder) {
        if (dataHolder.getDisplayableWithFilters() == null) {
            updateDisplayableWithFilters(dataHolder);
        }

        dataHolder.setShownDisplayable(
                fileService.correctGamma(dataHolder.getDisplayableWithFilters(), dataHolder.getShownGammaConverter()));
    }

    private void discardAllImages(UserProjectDataHolder dataHolder) {
        dataHolder.setDisplayableWithColorSpaceAndChannels(null);
        dataHolder.setDisplayableWithLinearGamma(null);
        dataHolder.setDisplayableWithDrawings(null);
        dataHolder.setDisplayableWithFilters(null);
        dataHolder.setShownDisplayable(null);
    }
}
