package ru.shurshavchiki.businessLogic.drawing.lineDrawers;

import ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers.LineBaseDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineTipDrawers.LineTipDrawer;
import ru.shurshavchiki.businessLogic.drawing.models.FigureOverlap;

import java.awt.*;
import java.awt.geom.Point2D;

public interface LineDrawer {
    FigureOverlap drawLine(Point2D start, Point2D end, float width);
    LineBaseDrawer getLineBaseDrawer();
    LineTipDrawer getStartLineTipDrawer();
    LineTipDrawer getEndLineTipDrawer();
    void setLineBaseDrawer(LineBaseDrawer lineBaseDrawer);
    void setStartLineTipDrawer(LineTipDrawer lineTipDrawer);
    void setEndLineTipDrawer(LineTipDrawer lineTipDrawer);
}
