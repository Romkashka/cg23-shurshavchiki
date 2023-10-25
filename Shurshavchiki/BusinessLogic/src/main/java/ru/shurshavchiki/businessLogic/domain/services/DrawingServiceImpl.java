package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers.LineBaseDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineTipDrawers.LineTipDrawer;

import java.awt.*;
import java.awt.geom.Point2D;

public class DrawingServiceImpl implements DrawingService {
    private final UserProjectDataHolder dataHolder;

    public DrawingServiceImpl(UserProjectDataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }

    @Override
    public void setLineBaseDrawer(String name) {
        dataHolder.setLineBaseDrawer(getLineBaseDrawerByName(name));
    }

    @Override
    public void setStartLineTipDrawer(String name) {
        dataHolder.setStartLineTipDrawer(getLineTipDrawerByName(name));
    }

    @Override
    public void setEndLineTipDrawer(String name) {
        dataHolder.setEndLineTipDrawer(getLineTipDrawerByName(name));
    }

    @Override
    public void drawLine(Point2D start, Point2D end, RgbConvertable color, float width) {

    }

    private LineBaseDrawer getLineBaseDrawerByName(String name) {
        return dataHolder.getLineBaseRepository().getLineBaseDrawerByName(name);
    }

    private LineTipDrawer getLineTipDrawerByName(String name) {
        return dataHolder.getLineTipRepository().getLineTipDrawerByName(name);
    }
}
