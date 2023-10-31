package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.drawing.lineDrawers.LineDrawer;
import ru.shurshavchiki.businessLogic.drawing.models.Drawing;
import ru.shurshavchiki.businessLogic.drawing.models.FigureOverlap;
import ru.shurshavchiki.businessLogic.drawing.models.Line;

public class DrawingServiceImpl implements DrawingService {
    @Override
    public Drawing drawLine(Line line, LineDrawer lineDrawer) {
        FigureOverlap overlap = lineDrawer.drawLine(line.start(), line.end(), line.width());
        return new Drawing(overlap, line.color());
    }
}
