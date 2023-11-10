package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.drawing.lineDrawers.LineDrawer;
import ru.shurshavchiki.businessLogic.drawing.models.Drawing;
import ru.shurshavchiki.businessLogic.drawing.models.FigureOverlap;
import ru.shurshavchiki.businessLogic.drawing.models.Line;
import ru.shurshavchiki.businessLogic.drawing.util.DrawingMerger;

import java.util.List;

public class DrawingServiceImpl implements DrawingService {
    @Override
    public Drawing drawLine(Line line, LineDrawer lineDrawer) {
        FigureOverlap overlap = lineDrawer.drawLine(line.start(), line.end(), (int) line.width(), line.alpha());
        return new Drawing(overlap, line.color());
    }

    @Override
    public Displayable mergeDrawings(Displayable source, Drawing drawing) {
        DrawingMerger drawingMerger = new DrawingMerger();
        return drawingMerger.addDrawing(source, drawing);
    }

    @Override
    public Displayable mergeDrawings(Displayable source, List<Drawing> drawings) {
        Displayable intermediateResult = source.clone();
        for (Drawing drawing: drawings) {
            intermediateResult = mergeDrawings(intermediateResult, drawing);
        }

        return intermediateResult;
    }
}
