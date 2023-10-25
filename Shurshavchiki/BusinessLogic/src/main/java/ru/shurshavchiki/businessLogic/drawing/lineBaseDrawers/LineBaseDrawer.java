package ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers;

import ru.shurshavchiki.businessLogic.drawing.models.FigureOverlap;

import java.awt.*;
import java.awt.geom.Point2D;

public interface LineBaseDrawer {
    String getName();
    FigureOverlap drawLineBase(Point2D start, Point2D end, float width);
}
