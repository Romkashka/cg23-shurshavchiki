package ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers;

import ru.shurshavchiki.businessLogic.domain.util.WithName;
import ru.shurshavchiki.businessLogic.drawing.models.FigureOverlap;

import java.awt.geom.Point2D;

public interface LineBaseDrawer extends WithName {
    FigureOverlap drawLineBase(Point2D start, Point2D end, float width);
}
