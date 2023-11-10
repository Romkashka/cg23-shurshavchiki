package ru.shurshavchiki.businessLogic.imageProcessing.scaling.implementations;

import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters.ScalingAlgorithmParameter;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class LanczosScalingAlgorithm extends ScalingAlgorithmBase {
    @Override
    public String getName() {
        return "Lanczos3";
    }

    @Override
    public void init(List<ScalingAlgorithmParameter> parameters) {
        kernelRadius = 3;
    }

    @Override
    public List<ScalingAlgorithmParameter> getParametersToInit() {
        return List.of();
    }

    @Override
    protected RgbConvertable calculateColor(Point2D point, Window window) {
        List<PositionedPixel> intermediatePixels = new ArrayList<>();
        for (int i = 0; i < kernelRadius; i++) {
            RgbConvertable resultColor = new RgbPixel(0);
            for (int j = 0; j < kernelRadius; j++) {
                PositionedPixel currentPixel = window.Pixels().get(i).get(j);
                RgbConvertable tmp = calculateColor(currentPixel.Color(), Point2D.distance(point.getX(), currentPixel.coordinates().getY(), currentPixel.coordinates().getX(), currentPixel.coordinates().getY()));
                resultColor = colorSum(resultColor, tmp);
            }
            intermediatePixels.add(new PositionedPixel(resultColor, new Point2D.Double(point.getX(), window.Pixels().get(i).get(0).coordinates().getY())));
        }

        RgbConvertable resultColor = new RgbPixel(0);
        for (int i = 0; i < kernelRadius; i++) {
            PositionedPixel currentPixel = intermediatePixels.get(i);
            RgbConvertable tmp = calculateColor(currentPixel.Color(), Point2D.distance(point.getX(), point.getY(), currentPixel.coordinates().getX(), currentPixel.coordinates().getY()));
            resultColor = colorSum(resultColor, tmp);
        }

        return resultColor;
    }

    private RgbConvertable calculateColor(RgbConvertable pixel, double dist) {
        return new RgbPixel(
                calculateChannel(pixel.FloatRed(), dist),
                calculateChannel(pixel.FloatGreen(), dist),
                calculateChannel(pixel.FloatBlue(), dist)
        );
    }

    float calculateChannel(float value, double dist) {
        if (dist < 0.0001) {
            return value;
        }
        if (Math.abs(dist) > kernelRadius) {
            return 0;
        }

        return (float) (value * kernelRadius * Math.sin(Math.PI * dist) * Math.sin(Math.PI * dist / kernelRadius) / (Math.PI * Math.PI * dist * dist));
    }
    RgbConvertable colorSum(RgbConvertable color1, RgbConvertable color2) {
        return new RgbPixel(color1.FloatRed() + color2.FloatRed(),
                color1.FloatGreen() + color2.FloatGreen(),
                color1.FloatBlue() + color2.FloatBlue());
    }
}
