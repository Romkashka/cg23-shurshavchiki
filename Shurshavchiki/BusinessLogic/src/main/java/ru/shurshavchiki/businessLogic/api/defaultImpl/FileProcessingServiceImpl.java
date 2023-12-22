package ru.shurshavchiki.businessLogic.api.defaultImpl;

import lombok.NonNull;
import ru.shurshavchiki.businessLogic.api.Context;
import ru.shurshavchiki.businessLogic.api.FileProcessingService;
import ru.shurshavchiki.businessLogic.api.UserProjectDataHolder;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.services.FileService;
import ru.shurshavchiki.businessLogic.domain.services.ImageParametersChangers;
import ru.shurshavchiki.businessLogic.domain.services.ImageProcessingService;

import java.io.File;
import java.io.IOException;
import java.util.zip.DataFormatException;

public class FileProcessingServiceImpl implements FileProcessingService {
    private final FileService fileService;
    private final ImageProcessingService imageProcessingService;
    private final DataHolderUpdateWizard dataHolderUpdateWizard;

    public FileProcessingServiceImpl(FileService fileService, ImageProcessingService imageProcessingService, DataHolderUpdateWizard dataHolderUpdateWizard) {
        this.fileService = fileService;
        this.imageProcessingService = imageProcessingService;
        this.dataHolderUpdateWizard = dataHolderUpdateWizard;
    }

    @Override
    public void createNewImage(@NonNull Context context) throws IOException {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        Displayable image = imageProcessingService.createNewImage(dataHolder.getImageCreationAlgorithm(),
                dataHolder.getNewImageHeader().getHeight(),
                dataHolder.getNewImageHeader().getWidth());
        dataHolder.setStartingDisplayable(image);
        resetToDefaultSettings(dataHolder);
        dataHolderUpdateWizard.forceUpdateDataHolder(dataHolder);
    }

    @Override
    public void openImage(@NonNull Context context) throws IOException, DataFormatException {
        UserProjectDataHolder userProjectDataHolder = context.getDataHolder();
        resetToDefaultSettings(userProjectDataHolder);
        userProjectDataHolder.setStartingDisplayable(fileService.readFile(userProjectDataHolder.getFile(), new ImageParametersChangers(
                        userProjectDataHolder.getColorSpaceFactory().getColorSpaceConverter(),
                        userProjectDataHolder.getChannelChooser(),
                        userProjectDataHolder.getInputGammaConverter()
                )));
        userProjectDataHolder.setDisplayableWithColorSpaceAndChannels(userProjectDataHolder.getStartingDisplayable());
        dataHolderUpdateWizard.updateDisplayableWithLinearGamma(userProjectDataHolder);
    }

    @Override
    public void saveImage(@NonNull Context context) throws IOException {
        saveImageAs(context, extractDataHolder(context).getFile());
    }

    @Override
    public void saveImageAs(@NonNull Context context, @NonNull File destination) throws IOException {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        fileService.saveFile(dataHolder.getShownDisplayable(),
                destination,
                new ImageParametersChangers(dataHolder.getColorSpaceFactory().getColorSpaceConverter(),
                dataHolder.getChannelChooser(), dataHolder.getInputGammaConverter()));
    }

    private UserProjectDataHolder extractDataHolder(@NonNull Context context) {
        return context.getDataHolder();
    }

    private void resetToDefaultSettings(UserProjectDataHolder userProjectDataHolder) {
        userProjectDataHolder.setInputGammaConverter(userProjectDataHolder.getGammaConvertersRegistry().getGammaConverter(0));
        userProjectDataHolder.setShownGammaConverter(userProjectDataHolder.getGammaConvertersRegistry().getGammaConverter(0));
    }
}
