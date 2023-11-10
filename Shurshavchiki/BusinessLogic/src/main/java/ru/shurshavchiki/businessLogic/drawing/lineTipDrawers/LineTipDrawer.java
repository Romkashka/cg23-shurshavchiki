package ru.shurshavchiki.businessLogic.drawing.lineTipDrawers;

import ru.shurshavchiki.businessLogic.domain.util.WithName;
import ru.shurshavchiki.businessLogic.drawing.models.FigureOverlap;
import ru.shurshavchiki.businessLogic.drawing.models.Vector;

import java.awt.geom.Point2D;

public interface LineTipDrawer extends WithName {
    FigureOverlap drawLineEnd(Point2D start, Vector vectorToLineCenter, float width, float alpha);
}
