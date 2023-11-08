package ru.shurshavchiki.businessLogic.api;

import ru.shurshavchiki.businessLogic.api.defaultImpl.ConversionServiceImpl;
import ru.shurshavchiki.businessLogic.api.defaultImpl.DataHolderUpdateWizard;
import ru.shurshavchiki.businessLogic.api.defaultImpl.FileProcessingServiceImpl;
import ru.shurshavchiki.businessLogic.domain.services.DrawingServiceImpl;
import ru.shurshavchiki.businessLogic.domain.services.FileService;
import ru.shurshavchiki.businessLogic.domain.services.FileServiceImpl;
import ru.shurshavchiki.businessLogic.domain.services.ImageProcessingServiceImpl;

public class ServiceFactoryImpl implements ServicesFactory {
    private final FileService fileService;
    private final ru.shurshavchiki.businessLogic.domain.services.ImageProcessingService imageProcessingService;
    private final ru.shurshavchiki.businessLogic.domain.services.DrawingService drawingService;
    private final DataHolderUpdateWizard dataHolderUpdateWizard;

    public ServiceFactoryImpl() {
        fileService = new FileServiceImpl();
        imageProcessingService = new ImageProcessingServiceImpl();
        drawingService = new DrawingServiceImpl();

        dataHolderUpdateWizard = new DataHolderUpdateWizard(fileService, drawingService);
    }


    @Override
    public Context getBlankContext() {
        return new DataHolderAdapter(new UserProjectDataHolderImpl());
    }

    @Override
    public FileProcessingService getFileProcessingService() {
        return new FileProcessingServiceImpl(fileService, imageProcessingService, dataHolderUpdateWizard);
    }

    @Override
    public ConversionService getConversionService() {
        return new ConversionServiceImpl(dataHolderUpdateWizard);
    }

    @Override
    public ImageProcessingService getImageProcessingService() {
        return new ru.shurshavchiki.businessLogic.api.defaultImpl.ImageProcessingServiceImpl(imageProcessingService, fileService, dataHolderUpdateWizard);
    }

    @Override
    public DrawingService getDrawingService() {
        return new ru.shurshavchiki.businessLogic.api.defaultImpl.DrawingServiceImpl(drawingService, dataHolderUpdateWizard);
    }
}
