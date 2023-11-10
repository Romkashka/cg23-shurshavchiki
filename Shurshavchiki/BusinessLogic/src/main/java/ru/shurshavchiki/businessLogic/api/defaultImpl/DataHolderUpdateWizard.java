package ru.shurshavchiki.businessLogic.api.defaultImpl;

import ru.shurshavchiki.businessLogic.api.UserProjectDataHolder;
import ru.shurshavchiki.businessLogic.domain.services.DrawingService;
import ru.shurshavchiki.businessLogic.domain.services.FileService;
import ru.shurshavchiki.businessLogic.exceptions.DataHolderUpdateException;

public class DataHolderUpdateWizard {
    private final FileService fileService;
    private final DrawingService drawingService;

    public DataHolderUpdateWizard(FileService fileService, DrawingService drawingService) {
        this.fileService = fileService;
        this.drawingService = drawingService;
    }

    public void forceUpdateDataHolder(UserProjectDataHolder dataHolder) {
        discardAllImages(dataHolder);

        updateShownDisplayable(dataHolder);
    }

    public void updateDisplayableWithFilters(UserProjectDataHolder dataHolder) {
        if (dataHolder.getStartingDisplayable() == null) {
            throw DataHolderUpdateException.NoImageToUpdate();
        }

        dataHolder.setDisplayableWithFilters(fileService.applyColorFilters(dataHolder.getStartingDisplayable(),
                dataHolder.getColorSpaceFactory().getColorSpaceConverter(),
                dataHolder.getChannelChooser()));
        dataHolder.setColorSpaceChanged(false);

        updateDisplayableWithLinearGamma(dataHolder);
    }

    public void updateDisplayableWithLinearGamma(UserProjectDataHolder dataHolder) {
        if (dataHolder.getDisplayableWithFilters() == null) {
            updateDisplayableWithFilters(dataHolder);
        }

        dataHolder.setDisplayableWithLinearGamma(
                fileService.useGamma(dataHolder.getDisplayableWithFilters(), dataHolder.getInputGammaConverter()));

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

        dataHolder.setStartingDisplayable(
                drawingService.mergeDrawings(dataHolder.getDisplayableWithLinearGamma(), dataHolder.getDrawings()));

        dataHolder.deleteDrawing(0);
        updateDisplayableWithFilters(dataHolder);
//        updateShownDisplayable(dataHolder);
    }

    public void updateShownDisplayable(UserProjectDataHolder dataHolder) {
        if (dataHolder.getDisplayableWithDrawings() == null) {
            updateDisplayableWithDrawings(dataHolder);
        }

        dataHolder.setShownDisplayable(
                fileService.correctGamma(dataHolder.getDisplayableWithDrawings(), dataHolder.getShownGammaConverter()));
    }

    private void discardAllImages(UserProjectDataHolder dataHolder) {
        dataHolder.setDisplayableWithFilters(null);
        dataHolder.setDisplayableWithLinearGamma(null);
        dataHolder.setDisplayableWithDrawings(null);
        dataHolder.setShownDisplayable(null);
    }
}
