package ru.shurshavchiki.businessLogic.domain.deprecatedServices;

import lombok.Getter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithmWithBitRate;

import java.awt.geom.Point2D;
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
        imageProcessingService = new ImageProcessingServiceImpl();
        drawingService = new DrawingServiceImpl(this.userProjectDataHolder);
    }

    @Override
    public void createNewImage(int height, int width) {
        resetToDefaultSettings();
//        userProjectDataHolder.setStartingDisplayable(imageProcessingService.createNewImage(height, width));
        userProjectDataHolder.setDisplayableWithFilters(userProjectDataHolder.getStartingDisplayable());
        userProjectDataHolder.setShownDisplayable(userProjectDataHolder.getDisplayableWithFilters());
        userProjectDataHolder.setFile(null);
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
//        fileService.chooseChannel(channelNames);
    }

    @Override
    public void chooseColorSpace(String colorSpaceName) {
//        fileService.chooseColorSpace(colorSpaceName);
    }

    @Override
    public void assignGamma(float gamma) {
//        fileService.assignGamma(gamma);
    }

    @Override
    public void convertGamma(float gamma) {
//        fileService.convertGamma(gamma);
    }

    @Override
    public void drawLine(RgbConvertable color, float width, Point2D start, Point2D end) {
        drawingService.drawLine(start, end,color,width);
    }

    @Override
    public void ditherImage(File destination) throws IOException {
//        Displayable result = imageProcessingService.ditherImage();
//        fileService.saveFile(result, destination);
    }

    @Override
    public List<String> getAllLineBases() {
        return userProjectDataHolder.getLineBaseRepository().getAllImplementations();
    }

    @Override
    public List<String> getAllLineTips() {
        return userProjectDataHolder.getLineTipRepository().getAllImplementations();
    }

    @Override
    public List<String> getAllImageCreationAlgorithms() {
        return userProjectDataHolder.getImageCreationAlgorithmRepository().getAllImplementations();
    }

    @Override
    public List<String> getAllDitheringAlgorithms() {
        return userProjectDataHolder.getDitheringAlgorithmRepository().getAllImplementations();
    }

    @Override
    public boolean isDitheringAlgorithmWithBitRate(String name) {
        return userProjectDataHolder.getDitheringAlgorithmRepository().getImplementationByName(name) instanceof DitheringAlgorithmWithBitRate;
    }

    @Override
    public void setDitheringAlgorithmBitRate(int bitRate) {
        if (userProjectDataHolder.getDitheringAlgorithm() instanceof DitheringAlgorithmWithBitRate algorithm) {
            algorithm.setBitRate(bitRate);
            return;
        }

        throw new UnsupportedOperationException("Bit rate can't be set for this dithering algorithm");
    }

    @Override
    public void setLineBase(String name) {
        drawingService.setLineBaseDrawer(name);
    }

    @Override
    public void setStartLineTip(String name) {
        drawingService.setStartLineTipDrawer(name);
    }

    @Override
    public void setEndLineTip(String name) {
        drawingService.setEndLineTipDrawer(name);
    }

    @Override
    public void setImageCreationAlgorithm(String name) {
        userProjectDataHolder.setImageCreationAlgorithm(userProjectDataHolder.getImageCreationAlgorithmRepository().getImplementationByName(name));
    }

    @Override
    public void setDitheringAlgorithm(String name) {
        userProjectDataHolder.setDitheringAlgorithm(userProjectDataHolder.getDitheringAlgorithmRepository().getImplementationByName(name));
    }

    private void resetToDefaultSettings() {
        userProjectDataHolder.setInputGammaConverter(userProjectDataHolder.getGammaConvertersRegistry().getGammaConverter(0));
        userProjectDataHolder.setShownGammaConverter(userProjectDataHolder.getGammaConvertersRegistry().getGammaConverter(0));
    }

    private static class DispatcherServiceHolder {
        private static final DispatcherService INSTANCE = new DispatcherServiceImpl();
    }
}
