package ru.shurshavchiki.businessLogic.imageProcessing.scaling.implementations;

import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters.ScalingAlgorithmParameter;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class BilinearScalingAlgorithm extends ScalingAlgorithmBase {
    @Override
    public String getName() {
        return "Bilinear";
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
        List<PositionedPixel> intermediatePixels = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            PositionedPixel leftPixel = window.Pixels().get(i).get(0);
            PositionedPixel rightPixel = window.Pixels().get(i).get(1);
            double leftDistance = Point2D.distance(point.getX(), leftPixel.coordinates().getY(), leftPixel.coordinates().getX(), leftPixel.coordinates().getY());
            intermediatePixels.add(new PositionedPixel(calculateColor(leftPixel.Color(),
                    rightPixel.Color(),
                    leftDistance,
                    Point2D.distance(point.getX(), leftPixel.coordinates().getY(), rightPixel.coordinates().getX(), rightPixel.coordinates().getY())),
                    new Point2D.Double(leftPixel.coordinates().getX() + leftDistance, leftPixel.coordinates().getY())));
        }

        return calculateColor(intermediatePixels.get(0).Color(),
                intermediatePixels.get(1).Color(),
                Point2D.distance(intermediatePixels.get(0).coordinates().getX(), intermediatePixels.get(0).coordinates().getY(), point.getX(), point.getY()),
                Point2D.distance(intermediatePixels.get(1).coordinates().getX(), intermediatePixels.get(1).coordinates().getY(), point.getX(), point.getY())
        );
    }

    private RgbConvertable calculateColor(RgbConvertable pixel1, RgbConvertable pixel2, double dist1, double dist2) {
        return new RgbPixel(
                calculateChannel(pixel1.FloatRed(), pixel2.FloatRed(), dist1, dist2),
                calculateChannel(pixel1.FloatGreen(), pixel2.FloatGreen(), dist1, dist2),
                calculateChannel(pixel1.FloatBlue(), pixel2.FloatBlue(), dist1, dist2)
        );
    }

    float calculateChannel(float value1, float value2, double dist1, double dist2) {
        return (float) ((value1 * dist2 + value2 * dist1) / (dist1 + dist2));
    }
}
