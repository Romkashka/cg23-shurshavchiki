package ru.shurshavchiki.businessLogic.api.defaultImpl;

import lombok.NonNull;
import ru.shurshavchiki.businessLogic.api.Context;
import ru.shurshavchiki.businessLogic.api.DrawingService;
import ru.shurshavchiki.businessLogic.api.UserProjectDataHolder;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.services.FileService;
import ru.shurshavchiki.businessLogic.drawing.models.Drawing;
import ru.shurshavchiki.businessLogic.drawing.models.Line;

public class DrawingServiceImpl implements DrawingService {
    private final ru.shurshavchiki.businessLogic.domain.services.DrawingService drawingService;
    private final FileService fileService;

    public DrawingServiceImpl(ru.shurshavchiki.businessLogic.domain.services.DrawingService drawingService, FileService fileService) {
        this.drawingService = drawingService;
        this.fileService = fileService;
    }


    @Override
    public void drawLine(@NonNull Context context, @NonNull Line line) {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        Drawing drawing = drawingService.drawLine(dataHolder.getNewLine(), dataHolder.getLineDrawer());
        dataHolder.addDrawing(drawing);
        dataHolder.setNewLine(null);
        Displayable withDrawings = drawingService.mergeDrawings(dataHolder.getDisplayableWithLinearGamma(), dataHolder.getDrawings());
        dataHolder.setDisplayableWithDrawings(withDrawings);
        Displayable resultingImage = fileService.useGamma(withDrawings, dataHolder.getShownGammaConverter());
        resultingImage = fileService.applyColorFilters(resultingImage, dataHolder.getColorSpaceFactory().getColorSpaceConverter(), dataHolder.getChannelChooser());
        dataHolder.setShownDisplayable(resultingImage);
        dataHolder.setDisplayableWithFilters(resultingImage);
    }

    private UserProjectDataHolder extractDataHolder(Context context) {
        return context.getDataHolder();
    }
}
