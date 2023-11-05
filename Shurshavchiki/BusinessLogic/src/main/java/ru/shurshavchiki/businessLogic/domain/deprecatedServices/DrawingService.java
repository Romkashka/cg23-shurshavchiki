package ru.shurshavchiki.businessLogic.domain.deprecatedServices;

import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;

import java.awt.geom.Point2D;

public interface DrawingService {
    void setLineBaseDrawer(String name);
    void setStartLineTipDrawer(String name);
    void setEndLineTipDrawer(String name);

    void drawLine(Point2D start, Point2D end, RgbConvertable color, float width);
}
