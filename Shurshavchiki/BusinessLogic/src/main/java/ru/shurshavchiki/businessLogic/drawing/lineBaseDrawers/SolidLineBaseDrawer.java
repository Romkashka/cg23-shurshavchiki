package ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers;

import jdk.jfr.Percentage;
import ru.shurshavchiki.businessLogic.drawing.models.FigureOverlap;
import ru.shurshavchiki.businessLogic.drawing.models.PixelOverlap;

import java.awt.geom.Point2D;
import java.util.*;

import static java.lang.Math.*;

public class SolidLineBaseDrawer implements LineBaseDrawer {
    private final float PrecisionCoefficient = 71F;
    private final float HalfOfCoeff = (int) (PrecisionCoefficient - 1F) / 2F;
    @Override
    public String getName() {
        return "Solid";
    }

    @Override
    public FigureOverlap drawLineBase(Point2D start, Point2D end, float width, float alpha) {
        FigureOverlap figure = new FigureOverlap();
        double pi = 3.14159265359;
        double EPSILON = 1e-2;
        class Bounds {
            public double left;
            public double right;

            public Bounds(double left, double right) {
                this.left = left;
                this.right = right;
            }

            @Override
            public String toString() {
                return left + " " + right;
            }
        }

        double x1, y1, x2, y2;
        x1 = start.getX() * PrecisionCoefficient;
        y1 = start.getY() * PrecisionCoefficient;
        x2 = end.getX() * PrecisionCoefficient;
        y2 = end.getY() * PrecisionCoefficient;
        width = width * PrecisionCoefficient / 2F;
        double angle = atan2(y2 - y1, x2 - x1);

        Point2D p1 = new Point2D.Double(x1 + width * cos(angle + pi / 2), y1 + width * sin(angle + pi / 2));
        Point2D p2 = new Point2D.Double(x1 + width * cos(angle - pi / 2), y1 + width * sin(angle - pi / 2));
        Point2D p3 = new Point2D.Double(x2 + width * cos(angle - pi / 2), y2 + width * sin(angle - pi / 2));
        Point2D p4 = new Point2D.Double(x2 + width * cos(angle + pi / 2), y2 + width * sin(angle + pi / 2));
        List<Point2D> rectangle = Arrays.asList(p1, p2, p3, p4);

        double minY = Double.MAX_VALUE;
        double maxY = Double.MIN_VALUE;
        for (Point2D point : rectangle) {
            minY = Math.min(minY, point.getY());
            maxY = Math.max(maxY, point.getY());
        }

        Map<Integer, Bounds> yBounds = new HashMap<>();
        for (int y = (int) floor(minY); y <= (int) ceil(maxY); y++) {
            double minX = Double.MAX_VALUE;
            double maxX = Double.MIN_VALUE;

            for (int i = 0; i < rectangle.size(); i++) {
                Point2D current = rectangle.get(i);
                Point2D next = rectangle.get((i + 1) % rectangle.size());

                if ((current.getY() <= y && next.getY() >= y) || (next.getY() <= y && current.getY() >= y)) {
                    double xIntersection = current.getX() + (y - current.getY()) * (next.getX() - current.getX()) / (next.getY() - current.getY());

                    if (xIntersection >= Math.min(current.getX(), next.getX()) - EPSILON &&
                            xIntersection <= Math.max(current.getX(), next.getX()) + EPSILON) {
                        minX = Math.min(minX, xIntersection);
                        maxX = Math.max(maxX, xIntersection);
                    }
                }
            }
            yBounds.put(y, new Bounds(minX, maxX));
        }
        for (int y = (int) floor(minY); y <= (int) ceil(maxY); y += PrecisionCoefficient) {
            for (int x = (int) floor(yBounds.get(y).left * 0.95); x <= (int) ceil(yBounds.get(y).right * 1.05); x += PrecisionCoefficient) {
                float perc = 0;
                for (int i = (int) (x - HalfOfCoeff); i <= x + HalfOfCoeff; i++) {
                    for (int j = (int) (y - HalfOfCoeff); j <= y + HalfOfCoeff; j++) {
                        if (yBounds.containsKey(j)) {
                            if (i >= yBounds.get(j).left - EPSILON && i <= yBounds.get(j).right + EPSILON) {
                                perc += 1;
                            }
                        }
                    }
                }
                float percentage = perc / PrecisionCoefficient / PrecisionCoefficient * alpha;
                    figure.addAll(new PixelOverlap((int) (x / PrecisionCoefficient), (int) (y / PrecisionCoefficient), percentage));
            }
        }
        return figure;
    }
}
