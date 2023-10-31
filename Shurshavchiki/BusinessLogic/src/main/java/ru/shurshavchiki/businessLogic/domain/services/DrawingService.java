package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.drawing.lineDrawers.LineDrawer;
import ru.shurshavchiki.businessLogic.drawing.models.Drawing;
import ru.shurshavchiki.businessLogic.drawing.models.Line;

public interface DrawingService {
    Drawing drawLine(Line line, LineDrawer lineDrawer);
}
