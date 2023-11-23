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
        double [][] mask = new double[window.Pixels().size()][window.Pixels().get(0).size()];
        double sum = 0;

        for (int i = 0; i < window.Pixels().size(); i++) {
            for (int j = 0; j <  window.Pixels().get(0).size(); j++) {
                PositionedPixel currentPixel = window.Pixels().get(i).get(j);
                mask[i][j] = calculateCoefficient((currentPixel.coordinates().getX() - point.getX()) / windowPixelWidth)
                        * calculateCoefficient((currentPixel.coordinates().getY() - point.getY()) / windowPixelHeight);
                sum += mask[i][j];
            }
        }

        RgbConvertable resultColor = new RgbPixel(0);

        for (int i = 0; i < window.Pixels().size(); i++) {
            for (int j = 0; j <  window.Pixels().get(0).size(); j++) {
                PositionedPixel currentPixel = window.Pixels().get(i).get(j);
                resultColor = colorSum(resultColor, currentPixel.Color(), mask[i][j] / sum);
            }
        }

        resultColor = validateColor(resultColor);

        return resultColor;
    }

    protected float calculateCoefficient(double dist) {
        dist = Math.abs(dist);
        if (Math.abs(dist) < 1.0) {
            return (float) ((12.0 - (9.0 * parameterB) - (6 * parameterC)) * dist * dist * dist +
                            (-18.0 + 12.0 * parameterB + 6 * parameterC) * dist * dist +
                            (6.0 - 2.0 * parameterB)) / 6.f;
        }
        else if (Math.abs(dist) < 2.0) {
            return (float) (((-parameterB - (6.0 * parameterC)) * dist * dist * dist)
                    + ((6.0 * parameterB + 30.0 * parameterC) * dist * dist)
                    + ((-12.0 * parameterB - 48.0 * parameterC) * dist)
                    + (8.0 * parameterB + 24.0 * parameterC)) / 6.f;
        }
        return 0;
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
