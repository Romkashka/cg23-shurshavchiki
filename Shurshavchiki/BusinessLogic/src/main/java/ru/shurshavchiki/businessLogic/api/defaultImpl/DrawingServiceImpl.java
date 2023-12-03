package ru.shurshavchiki.businessLogic.api.defaultImpl;

import lombok.NonNull;
import ru.shurshavchiki.businessLogic.api.Context;
import ru.shurshavchiki.businessLogic.api.DrawingService;
import ru.shurshavchiki.businessLogic.api.UserProjectDataHolder;
import ru.shurshavchiki.businessLogic.drawing.models.Drawing;
import ru.shurshavchiki.businessLogic.drawing.models.Line;

public class DrawingServiceImpl implements DrawingService {
    private final ru.shurshavchiki.businessLogic.domain.services.DrawingService drawingService;
    private final DataHolderUpdateWizard dataHolderUpdateWizard;

    public DrawingServiceImpl(ru.shurshavchiki.businessLogic.domain.services.DrawingService drawingService, DataHolderUpdateWizard dataHolderUpdateWizard) {
        this.drawingService = drawingService;
        this.dataHolderUpdateWizard = dataHolderUpdateWizard;
    }


    @Override
    public void drawLine(@NonNull Context context) {
        UserProjectDataHolder dataHolder = extractDataHolder(context);
        Drawing drawing = drawingService.drawLine(dataHolder.getNewLine(), dataHolder.getLineDrawer());
        dataHolder.addDrawing(drawing);
        dataHolder.setNewLine(null);
        dataHolderUpdateWizard.updateDisplayableWithDrawings(dataHolder);
    }

    private UserProjectDataHolder extractDataHolder(Context context) {
        return context.getDataHolder();
    }
}
