package ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations;

import lombok.Getter;
import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.FloatAlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.IntegerAlgorithmParameter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.exceptions.FilterException;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;
import ru.shurshavchiki.businessLogic.imageProcessing.filters.ImageFilter;

import java.util.ArrayList;
import java.util.List;

public class CannyFilter extends ImageFilterBase {
    protected GaussianBlurFilter blurFilter;

    @Getter
    float[][] mask;

    float[][] M;

    protected float[][] g_l;

    protected float[][] g_h;

    float[][] alpha;

    Displayable afterBlur;

    protected float threshold1;

    protected float threshold2;

    @Override
    public String getName() {
        return "Canny edge detector";
    }

    @Override
    public void init(List<AlgorithmParameter> parameterList) {
        if (parameterList.size() != 3) {
            throw FilterException.InvalidParametersList();
        }

        float sigma = extractFloatValue(parameterList.get(0));

        isGrayFilter = true;

        threshold1 = extractFloatValue(parameterList.get(1)) / 100;
        threshold2 = extractFloatValue(parameterList.get(2)) / 100;
        if (threshold2 < threshold1) {
            var tmp = threshold1;
            threshold1 = threshold2;
            threshold2 = tmp;
        }

        blurFilter = new GaussianBlurFilter();
        blurFilter.init(List.of(new FloatAlgorithmParameter("Sigma", 0f, 5f, sigma),
                new IntegerAlgorithmParameter("Is monochrome", 0, 1, 1)));
    }

    @Override
    public Displayable applyFilter(Displayable source) {
        if (isGrayFilter)
            this.grayscaleSource = toGrayscale(source);

        Displayable result = grayscaleSource.clone();
        afterBlur = blurFilter.applyFilter(result);
        initMatrix();
        createBorders();

        for (int x = 0; x < grayscaleSource.getWidth(); x++) {
            for (int y = 0; y < grayscaleSource.getHeight(); y++) {
                afterBlur.setPixel(x, y, applyMask(x, y));
            }
        }

        return afterBlur;
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return List.of(
                new FloatAlgorithmParameter("Sigma", 0.1f, 12f, 5f),
                new FloatAlgorithmParameter("Threshold 1", 0f, 1f, 0.1f),
                new FloatAlgorithmParameter("Threshold 2", 0f, 1f, 0.3f)
        );
    }

    protected void initMatrix(){
        M = new float[afterBlur.getWidth()][afterBlur.getHeight()];
        alpha = new float[afterBlur.getWidth()][afterBlur.getHeight()];
        for (int x = 0; x < afterBlur.getWidth(); x++) {
            for (int y = 0; y < afterBlur.getHeight(); y++) {
                RgbConvertable[][] pixels = new RgbConvertable[3][3];
                for (int i = 0; i <= 2; ++i){
                    for (int j = 0; j <= 2; ++j){
                        pixels[i][j] = afterBlur.getPixel(returnPixelCoordinatesToBorders(x + i - 1, afterBlur.getWidth()),
                                returnPixelCoordinatesToBorders(y + j - 1, afterBlur.getHeight()));
                    }
                }
                float grad_y = pixels[0][2].FloatMean() + 2 * pixels[1][2].FloatMean() +
                        pixels[2][2].FloatMean() - pixels[0][0].FloatMean() -
                        2 * pixels[1][0].FloatMean() - pixels[2][0].FloatMean();
                float grad_x = pixels[2][0].FloatMean() + 2 * pixels[2][1].FloatMean() +
                        pixels[2][2].FloatMean() - pixels[0][0].FloatMean() -
                        2 * pixels[0][1].FloatMean() - pixels[0][2].FloatMean();
                M[x][y] = (float) Math.sqrt(grad_x * grad_x + grad_y * grad_y);
                alpha[x][y] = (float) Math.atan2(grad_y, grad_x);
            }
        }
    }

    protected void createBorders(){
        g_l = new float[afterBlur.getWidth()][afterBlur.getHeight()];
        g_h = new float[afterBlur.getWidth()][afterBlur.getHeight()];
        for (int x = 0; x < afterBlur.getWidth(); x++) {
            for (int y = 0; y < afterBlur.getHeight(); y++) {
                int d = getD(x, y);

                switch (d){
                    case 0:
                        if ((M[Math.max(Math.min(x + 1, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y - 1, afterBlur.getHeight() - 1), 0)] > M[x][y]
                                || M[Math.max(Math.min(x - 1, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y + 1, afterBlur.getHeight() - 1), 0)] > M[x][y])) {
                        }else{
                            if (M[x][y] > threshold1){
                                g_l[x][y] = M[x][y];
                                g_h[x][y] = M[x][y];
                            }
                            if (M[x][y] > threshold2)
                                g_h[x][y] = M[x][y];
                        }
                        break;
                    case 1:
                        if ((M[Math.max(Math.min(x - 1, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y, afterBlur.getHeight() - 1), 0)] > M[x][y]
                                || M[Math.max(Math.min(x + 1, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y, afterBlur.getHeight() - 1), 0)] > M[x][y])) {
                        }else{
                            if (M[x][y] > threshold1){
                                g_l[x][y] = M[x][y];
                                g_h[x][y] = M[x][y];
                            }
                            if (M[x][y] > threshold2)
                                g_h[x][y] = M[x][y];
                        }
                        break;
                    case 2:
                        if ((M[Math.max(Math.min(x - 1, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y - 1, afterBlur.getHeight() - 1), 0)] > M[x][y]
                                || M[Math.max(Math.min(x + 1, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y + 1, afterBlur.getHeight() - 1), 0)] > M[x][y])) {
                        }else{
                            if (M[x][y] > threshold1){
                                g_l[x][y] = M[x][y];
                                g_h[x][y] = M[x][y];
                            }
                            if (M[x][y] > threshold2)
                                g_h[x][y] = M[x][y];
                        }
                        break;
                    case 3:
                        if ((M[Math.max(Math.min(x, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y - 1, afterBlur.getHeight() - 1), 0)] > M[x][y]
                                || M[Math.max(Math.min(x, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y + 1, afterBlur.getHeight() - 1), 0)] > M[x][y])) {
                        }else{
                            if (M[x][y] > threshold1){
                                g_l[x][y] = M[x][y];
                                g_h[x][y] = M[x][y];
                            }
                            if (M[x][y] > threshold2)
                                g_h[x][y] = M[x][y];
                        }
                        break;
                }
            }
        }
    }

    private int getD(int x, int y) {
        int d = 0;
        if (Math.toDegrees(alpha[x][y]) > -22.5 && Math.toDegrees(alpha[x][y]) < 22.5)
            d = 1;
        else if (Math.toDegrees(alpha[x][y]) < -157.5 || Math.toDegrees(alpha[x][y]) > 157.5)
            d = 1;
        else if (Math.toDegrees(alpha[x][y]) >= 22.5 && Math.toDegrees(alpha[x][y]) <= 67.5)
            d = 2;
        else if (Math.toDegrees(alpha[x][y]) >= -157.5 && Math.toDegrees(alpha[x][y]) <= -112.5)
            d = 2;
        else if (Math.toDegrees(alpha[x][y]) > 67.5 && Math.toDegrees(alpha[x][y]) < 112.5)
            d = 3;
        else if (Math.toDegrees(alpha[x][y]) > -112.5 && Math.toDegrees(alpha[x][y]) < -67.5)
            d = 3;
        return d;
    }

    @Override
    protected RgbConvertable applyMask(int x, int y) {
        RgbConvertable pixel = new RgbPixel(g_l[x][y]);

        if (g_h[Math.max(Math.min(x + 1, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y - 1, afterBlur.getHeight() - 1), 0)] == 0
                && g_h[Math.max(Math.min(x + 1, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y, afterBlur.getHeight() - 1), 0)] == 0
                && g_h[Math.max(Math.min(x + 1, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y + 1, afterBlur.getHeight() - 1), 0)] == 0
                && g_h[Math.max(Math.min(x, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y - 1, afterBlur.getHeight() - 1), 0)] == 0
                && g_h[Math.max(Math.min(x, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y, afterBlur.getHeight() - 1), 0)] == 0
                && g_h[Math.max(Math.min(x, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y + 1, afterBlur.getHeight() - 1), 0)] == 0
                && g_h[Math.max(Math.min(x - 1, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y - 1, afterBlur.getHeight() - 1), 0)] == 0
                && g_h[Math.max(Math.min(x - 1, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y, afterBlur.getHeight() - 1), 0)] == 0
                && g_h[Math.max(Math.min(x - 1, afterBlur.getWidth() - 1), 0)][Math.max(Math.min(y + 1, afterBlur.getHeight() - 1), 0)] == 0)
            pixel = new RgbPixel(0);

        return pixel;
    }
}
