package ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations;

import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.FloatAlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.IntegerAlgorithmParameter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.exceptions.FilterException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class CASFilter extends ImageFilterBase {
    protected float developer_max;
    @Override
    public String getName() {
        return "CAS";
    }

    @Override
    public void init(List<AlgorithmParameter> parameterList) {
        if (parameterList.size() != 2) {
            throw FilterException.InvalidParametersList();
        }

        maskRadius = 1;
        developer_max = lerp(-0.125f, -0.2f, extractFloatValue(parameterList.get(0)));
        isGrayFilter = extractIntValue(parameterList.get(1)) == 1;
    }

    protected float lerp(float start, float end, float t) {
        return start * (1 - t) + end * t;
    }

    @Override
    protected RgbConvertable applyMask(int x, int y) {
        List<RgbConvertable> pixels = new ArrayList<>();

        pixels.add(grayscaleSource.getPixel(returnPixelCoordinatesToBorders(x, grayscaleSource.getWidth()),
                returnPixelCoordinatesToBorders(y, grayscaleSource.getHeight())));
        pixels.add(grayscaleSource.getPixel(returnPixelCoordinatesToBorders(x - maskRadius, grayscaleSource.getWidth()),
                returnPixelCoordinatesToBorders(y, grayscaleSource.getHeight())));
        pixels.add(grayscaleSource.getPixel(returnPixelCoordinatesToBorders(x + maskRadius, grayscaleSource.getWidth()),
                returnPixelCoordinatesToBorders(y, grayscaleSource.getHeight())));
        pixels.add(grayscaleSource.getPixel(returnPixelCoordinatesToBorders(x, grayscaleSource.getWidth()),
                returnPixelCoordinatesToBorders(y - maskRadius, grayscaleSource.getHeight())));
        pixels.add(grayscaleSource.getPixel(returnPixelCoordinatesToBorders(x, grayscaleSource.getWidth()),
                returnPixelCoordinatesToBorders(y + maskRadius, grayscaleSource.getHeight())));

        float min_g = 1;
        float max_g = 0;
        for (var pixel : pixels){
            min_g = Math.min(pixel.FloatGreen(), min_g);
            max_g = Math.max(pixel.FloatGreen(), max_g);
        }
        float d_max_g = 1 - max_g;

        float A;
        if (d_max_g < min_g)
            A = d_max_g / max_g;
        else
            A = min_g / max_g;

        A = (float) Math.sqrt(A);
        float w = A * developer_max;

        if (!isGrayFilter)
            return new RgbPixel(
                (pixels.get(0).FloatRed() + w * pixels.get(1).FloatRed() + w * pixels.get(2).FloatRed()
                        + w * pixels.get(3).FloatRed() + w * pixels.get(4).FloatRed()) / (4 * w + 1),
                (pixels.get(0).FloatGreen() + w * pixels.get(1).FloatGreen() + w * pixels.get(2).FloatGreen()
                        + w * pixels.get(3).FloatGreen() + w * pixels.get(4).FloatGreen()) / (4 * w + 1),
                (pixels.get(0).FloatBlue() + w * pixels.get(1).FloatBlue() + w * pixels.get(2).FloatBlue()
                        + w * pixels.get(3).FloatBlue() + w * pixels.get(4).FloatBlue()) / (4 * w + 1));

        return new RgbPixel((pixels.get(0).FloatGreen() + w * pixels.get(1).FloatGreen() + w * pixels.get(2).FloatGreen()
                + w * pixels.get(3).FloatGreen() + w * pixels.get(4).FloatGreen()) / (4 * w + 1));
    }

    @Override
    protected Displayable normalize(Displayable displayable) {
        List<List<RgbConvertable>> result = new ArrayList<>();

        for (var row: displayable.getAllPixels()) {
            List<RgbConvertable> currentRow = new ArrayList<>();
            for (var pixel: row) {
                RgbConvertable newPixel = new RgbPixel(Math.min(1f, Math.max(0f, pixel.FloatRed())),
                        Math.min(1f, Math.max(0f, pixel.FloatGreen())), Math.min(1f, Math.max(0f, pixel.FloatBlue())));

                currentRow.add(newPixel);
            }
            result.add(currentRow);
        }

        return new PnmFile(displayable.getHeader(), result);
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return List.of(
                new FloatAlgorithmParameter("Sharpness", 0f, 1f, 0.5f),
                new IntegerAlgorithmParameter("Is monochrome", 0, 1, 0)
        );
    }
}
