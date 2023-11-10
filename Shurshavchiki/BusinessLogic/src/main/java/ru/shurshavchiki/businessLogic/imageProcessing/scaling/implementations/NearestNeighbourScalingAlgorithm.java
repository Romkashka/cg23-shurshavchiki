package ru.shurshavchiki.businessLogic.imageProcessing.scaling.implementations;

import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters.ScalingAlgorithmParameter;

import java.awt.geom.Point2D;
import java.util.List;

public class NearestNeighbourScalingAlgorithm extends ScalingAlgorithmBase {
    @Override
    public String getName() {
        return "Nearest Neighbour";
    }

    @Override
    public void init(List<ScalingAlgorithmParameter> parameters) {
        kernelRadius = 1;
    }

    @Override
    public List<ScalingAlgorithmParameter> getParametersToInit() {
        return List.of();
    }

    @Override
    protected RgbConvertable calculateColor(Point2D point, Window window) {
        double minDistance = Double.MAX_VALUE;
        RgbConvertable color = null;
        for (var row: window.Pixels()) {
            for (PositionedPixel pixel: row) {
                double currentDistance = Point2D.distance(point.getX(), point.getY(), pixel.coordinates().getX(), pixel.coordinates().getY());
                if (currentDistance < minDistance) {
                    minDistance = currentDistance;
                    color = pixel.Color();
                }
            }
        }

        return color;
    }
}
