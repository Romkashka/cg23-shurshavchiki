package ru.shurshavchiki.businessLogic.drawing.lineTipDrawers;

import ru.shurshavchiki.businessLogic.drawing.models.Vector;
import ru.shurshavchiki.businessLogic.drawing.models.FigureOverlap;

import java.awt.*;
import java.awt.geom.Point2D;

public interface LineTipDrawer {
    String getName();
    FigureOverlap drawLineEnd(Point2D start, Vector vectorToLineCenter, float width);
}
