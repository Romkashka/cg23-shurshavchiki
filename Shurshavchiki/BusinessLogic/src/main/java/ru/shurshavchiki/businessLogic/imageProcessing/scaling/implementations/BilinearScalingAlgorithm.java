package ru.shurshavchiki.businessLogic.imageProcessing.scaling.implementations;

import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters.ScalingAlgorithmParameter;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

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
        int windowHeight = window.Pixels().size();
        int windowWidth = window.Pixels().get(0).size();
        double [][] mask = new double[windowHeight][windowWidth];
        double sum = 0;
        for (int i = 0; i < windowHeight; i++) {
            for (int j = 0; j < windowWidth; j++) {
                PositionedPixel currentPixel = window.Pixels().get(i).get(j);
                PositionedPixel currentPixelX = window.Pixels().get(i).get(windowWidth - j - 1);
                PositionedPixel currentPixelY = window.Pixels().get(windowHeight - i - 1).get(j);
//                mask[i][j] = calculateCoefficient(abs((currentPixel.coordinates().getX() - point.getX()) / windowPixelWidth), abs((currentPixelX.coordinates().getX() - point.getX()) / windowPixelWidth))
//                        * calculateCoefficient(abs((currentPixel.coordinates().getY() - point.getY()) / windowPixelHeight), abs((currentPixelY.coordinates().getY() - point.getY()) / windowPixelHeight));
                mask[i][j] = calculateWeight(abs(currentPixel.coordinates().getX() - point.getX()), windowPixelHeight * (windowHeight) / 2.0) *
                        calculateWeight(abs(currentPixel.coordinates().getY() - point.getY()), windowPixelWidth * (windowWidth) / 2.0);
                sum += mask[i][j];
            }
        }

        RgbConvertable resultColor = new RgbPixel(0);

        for (int i = 0; i < windowHeight; i++) {
            for (int j = 0; j < windowWidth; j++) {
                PositionedPixel currentPixel = window.Pixels().get(i).get(j);
                resultColor = colorSum(resultColor, currentPixel.Color(), mask[i][j] / sum);
            }
        }

        resultColor = validateColor(resultColor);

        return resultColor;
    }

    protected double calculateCoefficient(double dist1, double dist2) {
        if ((dist1 + dist2) < 0.00001)
            return 0;

        return (float) (abs(dist2) / (dist1 + dist2));
    }

    protected double calculateWeight(double dist, double radius) {
        return (radius - dist) / radius;
    }

    protected RgbConvertable colorSum(RgbConvertable color1, RgbConvertable color2, double coefficient) {
        return new RgbPixel(color1.FloatRed() + (float) coefficient * color2.FloatRed(),
                color1.FloatGreen() + (float) coefficient * color2.FloatGreen(),
                color1.FloatBlue() + (float) coefficient * color2.FloatBlue());
    }

    protected RgbConvertable validateColor(RgbConvertable color) {
        float r = Math.max(Math.min(color.FloatRed() * 1000f, 1000f), 0f) / 1000f;
        float g = Math.max(Math.min(color.FloatGreen() * 1000f, 1000f), 0f) / 1000f;
        float b = Math.max(Math.min(color.FloatBlue() * 1000f, 1000f), 0f) / 1000f;
        return new RgbPixel(r, g, b);
    }
}
