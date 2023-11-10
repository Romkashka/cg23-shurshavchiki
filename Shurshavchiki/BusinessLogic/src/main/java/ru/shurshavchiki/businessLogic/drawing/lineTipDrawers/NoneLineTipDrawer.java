package ru.shurshavchiki.businessLogic.drawing.lineTipDrawers;

import ru.shurshavchiki.businessLogic.drawing.models.FigureOverlap;
import ru.shurshavchiki.businessLogic.drawing.models.Vector;

import java.awt.geom.Point2D;

public class NoneLineTipDrawer implements LineTipDrawer {
    @Override
    public String getName() {
        return "None";
    }

    @Override
    public FigureOverlap drawLineEnd(Point2D start, Vector vectorToLineCenter, float width, float alpha) {
        return new FigureOverlap();
    }
}
