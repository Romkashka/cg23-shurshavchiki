package ru.shurshavchiki.businessLogic.imageProcessing.scaling.implementations;

import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters.FloatScalingAlgorithmParameter;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters.ScalingAlgorithmParameter;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class BCSplinesScalingAlgorithm extends ScalingAlgorithmBase {

    private float parameterB;

    private float parameterC;

    @Override
    public String getName() {
        return "BC-Splines";
    }

    @Override
    public void init(List<ScalingAlgorithmParameter> parameters) {
        kernelRadius = 2.5;
        for (var parameter : parameters){
            if (parameter.getName().equals("B")){
                if (parameter instanceof FloatScalingAlgorithmParameter floatParameter)
                    parameterB = floatParameter.getValue();
            }
            if (parameter.getName().equals("C")){
                if (parameter instanceof FloatScalingAlgorithmParameter floatParameter)
                    parameterC = floatParameter.getValue();
            }
        }
    }

    @Override
    public List<ScalingAlgorithmParameter> getParametersToInit() {
        return List.of(new FloatScalingAlgorithmParameter("B", 0, 1, 0),
                new FloatScalingAlgorithmParameter("C", 0, 1, 0.5f));
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
        if (Math.abs(dist) < 1) {
            return (float) (((12 - (9 * parameterB) - (6 * parameterC)) * dist * dist * dist) +
                            ((-18 + 12 * parameterB + 6 * parameterC) * dist * dist) +
                            (6 - 2 * parameterB)) * value / 6.f;
        }
        if (Math.abs(dist) < 2) {
            return (float) (((-parameterB - (6 * parameterC)) * dist * dist * dist)
                    + ((6 * parameterB + 30 * parameterC) * dist * dist)
                    + ((-12 * parameterB - 48 * parameterC) * dist)
                    + (8 * parameterB + 24 * parameterC)) * value / 6.f;
        }
        return 0;
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
