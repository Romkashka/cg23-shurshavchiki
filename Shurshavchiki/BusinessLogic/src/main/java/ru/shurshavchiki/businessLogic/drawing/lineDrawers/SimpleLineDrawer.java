package ru.shurshavchiki.businessLogic.drawing.lineDrawers;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers.LineBaseDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineTipDrawers.LineTipDrawer;
import ru.shurshavchiki.businessLogic.drawing.models.FigureOverlap;
import ru.shurshavchiki.businessLogic.drawing.models.Vector;

import java.awt.geom.Point2D;

public class SimpleLineDrawer implements LineDrawer {
    @Getter @Setter
    private LineBaseDrawer lineBaseDrawer;
    @Getter @Setter
    private LineTipDrawer startLineTipDrawer;
    @Getter @Setter
    private LineTipDrawer endLineTipDrawer;

    @Override
    public FigureOverlap drawLine(Point2D start, Point2D end, float width, float alpha) {
        FigureOverlap result = new FigureOverlap();
        Point2D center = new Point2D.Float((float) ((end.getX() + start.getX()) / 2F), (float) ((end.getY() + start.getY()) / 2F));
        System.out.println(222222222);
        result.addAll(lineBaseDrawer.drawLineBase(start, end, width, alpha).getPixelOverlaps());
        result.addAll(startLineTipDrawer.drawLineEnd(start, new Vector(start, center), width, alpha).getPixelOverlaps());
        result.addAll(endLineTipDrawer.drawLineEnd(start, new Vector(center, end), width, alpha).getPixelOverlaps());

        return result;
    }
}
