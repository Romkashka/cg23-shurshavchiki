package ru.shurshavchiki.businessLogic.api;

public interface ServicesFactory {
    Context getBlankContext();
    FileProcessingService getFileProcessingService();
    ConversionService getConversionService();
    ImageProcessingService getImageProcessingService();
    DrawingService getDrawingService();
}
