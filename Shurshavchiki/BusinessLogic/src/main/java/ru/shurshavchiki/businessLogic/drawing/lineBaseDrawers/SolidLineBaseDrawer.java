package ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers;

import ru.shurshavchiki.businessLogic.drawing.models.FigureOverlap;

import java.awt.geom.Point2D;

public class SolidLineBaseDrawer implements LineBaseDrawer {
    @Override
    public String getName() {
        return "Solid";
    }

    @Override
    public FigureOverlap drawLineBase(Point2D start, Point2D end, float width) {
        return null;
    }
}
