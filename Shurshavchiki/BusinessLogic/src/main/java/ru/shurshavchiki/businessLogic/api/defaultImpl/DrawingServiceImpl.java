package ru.shurshavchiki.businessLogic.api.defaultImpl;

import lombok.NonNull;
import ru.shurshavchiki.businessLogic.api.Context;
import ru.shurshavchiki.businessLogic.api.DrawingService;
import ru.shurshavchiki.businessLogic.api.UserProjectDataHolder;
import ru.shurshavchiki.businessLogic.drawing.models.Drawing;

public class DrawingServiceImpl implements DrawingService {
    private final ru.shurshavchiki.businessLogic.domain.services.DrawingService drawingService;

    public DrawingServiceImpl(ru.shurshavchiki.businessLogic.domain.services.DrawingService drawingService) {
        this.drawingService = drawingService;
    }


    @Override
    public void drawLine(@NonNull Context context) {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        Drawing drawing = drawingService.drawLine(dataHolder.getNewLine(), dataHolder.getLineDrawer());
        dataHolder.addDrawing(drawing);
        dataHolder.setNewLine(null);
        // TODO: add final image calculation
    }

    private UserProjectDataHolder extractDataHolder(Context context) {
        return context.getDataHolder();
    }
}
