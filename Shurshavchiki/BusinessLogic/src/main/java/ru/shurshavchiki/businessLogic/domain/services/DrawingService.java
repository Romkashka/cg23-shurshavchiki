package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.drawing.lineDrawers.LineDrawer;
import ru.shurshavchiki.businessLogic.drawing.models.Drawing;
import ru.shurshavchiki.businessLogic.drawing.models.Line;

import java.util.List;

public interface DrawingService {
    Drawing drawLine(Line line, LineDrawer lineDrawer);
    Displayable mergeDrawings(Displayable source, Drawing drawing);
    Displayable mergeDrawings(Displayable source, List<Drawing> drawings);
}
