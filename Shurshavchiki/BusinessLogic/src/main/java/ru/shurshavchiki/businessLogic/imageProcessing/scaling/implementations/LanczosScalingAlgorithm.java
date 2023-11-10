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
        kernelRadius = 3.5;
    }

    @Override
    public List<ScalingAlgorithmParameter> getParametersToInit() {
        return List.of();
    }

    @Override
    protected RgbConvertable calculateColor(Point2D point, Window window) {
        List<PositionedPixel> intermediatePixels = new ArrayList<>();
        for (int i = 0; i < kernelRadius * 2; i++) {
            RgbConvertable resultColor = new RgbPixel(0);
            for (int j = 0; j < kernelRadius * 2; j++) {
                PositionedPixel currentPixel = window.Pixels().get(i).get(j);
                RgbConvertable tmp = calculateColor(currentPixel.Color(), (currentPixel.coordinates().getX() - point.getX()) / sourcePixelWidth);
                resultColor = colorSum(resultColor, tmp);
            }

            resultColor = validateColor(resultColor);

            intermediatePixels.add(new PositionedPixel(resultColor, new Point2D.Double(point.getX(), window.Pixels().get(i).get(0).coordinates().getY())));
        }

        RgbConvertable resultColor = new RgbPixel(0);
        for (int i = 0; i < kernelRadius * 2; i++) {
            PositionedPixel currentPixel = intermediatePixels.get(i);
            RgbConvertable tmp = calculateColor(currentPixel.Color(), (currentPixel.coordinates().getY() - point.getY()) / sourcePixelHeight);
            resultColor = colorSum(resultColor, tmp);
        }

        resultColor = validateColor(resultColor);

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
        if (Math.abs(dist) < 0.0000001) {
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

    RgbConvertable validateColor(RgbConvertable color) {
        float r = Math.max(Math.min(color.FloatRed() * 1000f, 1000f), 0f) / 1000f;
        float g = Math.max(Math.min(color.FloatGreen() * 1000f, 1000f), 0f) / 1000f;
        float b = Math.max(Math.min(color.FloatBlue() * 1000f, 1000f), 0f) / 1000f;
        return new RgbPixel(r, g, b);
    }
}
