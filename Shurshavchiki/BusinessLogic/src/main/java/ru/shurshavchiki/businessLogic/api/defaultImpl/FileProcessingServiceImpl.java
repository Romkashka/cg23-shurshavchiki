package ru.shurshavchiki.businessLogic.api.defaultImpl;

import lombok.NonNull;
import ru.shurshavchiki.businessLogic.api.Context;
import ru.shurshavchiki.businessLogic.api.FileProcessingService;
import ru.shurshavchiki.businessLogic.api.UserProjectDataHolder;
import ru.shurshavchiki.businessLogic.domain.services.FileService;
import ru.shurshavchiki.businessLogic.domain.services.ImageProcessingService;

import java.io.File;
import java.io.IOException;

public class FileProcessingServiceImpl implements FileProcessingService {
    private final FileService fileService;
    private final ImageProcessingService imageProcessingService;

    public FileProcessingServiceImpl(FileService fileService, ImageProcessingService imageProcessingService) {
        this.fileService = fileService;
        this.imageProcessingService = imageProcessingService;
    }

    @Override
    public void createNewImage(@NonNull Context context) throws IOException {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        imageProcessingService.createNewImage(dataHolder.getImageCreationAlgorithm(),
                dataHolder.getNewImageHeader().getHeight(),
                dataHolder.getNewImageHeader().getWidth());
    }

    @Override
    public void openImage(@NonNull Context context) throws IOException {
        UserProjectDataHolder userProjectDataHolder = context.getDataHolder();
        userProjectDataHolder.setFile(userProjectDataHolder.getFile());
        resetToDefaultSettings(userProjectDataHolder);
        userProjectDataHolder.setStartingDisplayable(fileService.readFile(userProjectDataHolder.getFile(),
                userProjectDataHolder.getColorSpaceFactory().getColorSpaceConverter(),
                userProjectDataHolder.getChannelChooser()));
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
                dataHolder.getColorSpaceFactory().getColorSpaceConverter(),
                dataHolder.getChannelChooser());
    }

    private UserProjectDataHolder extractDataHolder(@NonNull Context context) {
        return context.getDataHolder();
    }

    private void resetToDefaultSettings(UserProjectDataHolder userProjectDataHolder) {
        userProjectDataHolder.setInputGammaConverter(userProjectDataHolder.getGammaConvertersRegistry().getGammaConverter(0));
        userProjectDataHolder.setShownGammaConverter(userProjectDataHolder.getGammaConvertersRegistry().getGammaConverter(0));
    }
}
