package ru.shurshavchiki.businessLogic.domain.services;

import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class DispatcherServiceImpl implements DispatcherService {
    @Getter
    private final UserProjectDataHolder userProjectDataHolder;
    private final FileService fileService;
    private final ImageProcessingService imageProcessingService;
    private final DrawingService drawingService;

    public static DispatcherService getInstance() {
        return DispatcherServiceHolder.INSTANCE;
    }

    private DispatcherServiceImpl() {
        userProjectDataHolder = new ProjectDataHolderImpl();
        fileService = new FileServiceImpl(this.userProjectDataHolder);
        imageProcessingService = new ImageProcessingServiceImpl(this.userProjectDataHolder);
        drawingService = new DrawingServiceImpl(this.userProjectDataHolder);
    }

    @Override
    public void readFile(File file) throws IOException {
        userProjectDataHolder.setFile(file);
        resetToDefaultSettings();
        userProjectDataHolder.setStartingDisplayable(fileService.readFile(file));
        fileService.render();
    }

    @Override
    public void saveCurrentFile(File file) throws IOException {
        fileService.saveFile(userProjectDataHolder.getShownDisplayable(), file);
    }

    @Override
    public List<String> getColorSpacesNames() {
        return fileService.getColorSpacesNames();
    }

    @Override
    public void chooseChannel(List<String> channelNames) {
        fileService.chooseChannel(channelNames);
    }

    @Override
    public void chooseColorSpace(String colorSpaceName) {
        fileService.chooseColorSpace(colorSpaceName);
    }

    @Override
    public void assignGamma(float gamma) {
        fileService.assignGamma(gamma);
    }

    @Override
    public void convertGamma(float gamma) {
        fileService.convertGamma(gamma);
    }

    private void resetToDefaultSettings() {
        userProjectDataHolder.setInputGammaConverter(userProjectDataHolder.getGammaConvertersRegistry().getGammaConverter(0));
        userProjectDataHolder.setShownGammaConverter(userProjectDataHolder.getGammaConvertersRegistry().getGammaConverter(0));
    }

    private static class DispatcherServiceHolder {
        private static final DispatcherService INSTANCE = new DispatcherServiceImpl();
    }
}
