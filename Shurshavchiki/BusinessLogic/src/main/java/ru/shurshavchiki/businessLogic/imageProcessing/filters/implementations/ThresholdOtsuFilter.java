package ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations;

import lombok.Getter;
import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.IntegerAlgorithmParameter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.exceptions.FilterException;

import java.util.List;

public class ThresholdOtsuFilter extends ImageLinearFilterBase {
    float threshold1;
    float threshold2;

    int cnt;

    @Getter
    float[][] mask;

    @Override
    public String getName() {
        return "Threshold Otsu Filter";
    }

    @Override
    public void init(List<AlgorithmParameter> parameterList) {
        if (parameterList.size() != 2) {
            throw FilterException.InvalidParametersList();
        }

        cnt = extractIntValue(parameterList.get(0));
        int value2 = extractIntValue(parameterList.get(1));

        maskRadius = 0;
        coefficient = 1f;
        isGrayFilter = value2 == 1;

    }

    protected void calculateThresholds(){
        int[] p = new int[256];

        float l = 3 * grayscaleSource.getWidth() * grayscaleSource.getHeight();
        float global_m = 0;
        for (int x = 0; x < grayscaleSource.getWidth(); x++) {
            for (int y = 0; y < grayscaleSource.getHeight(); y++) {
                var pixel = grayscaleSource.getPixel(x, y);
                p[pixel.Blue()]++;
                p[pixel.Red()]++;
                p[pixel.Green()]++;
                global_m += ((float) (pixel.Red() + pixel.Green() + pixel.Blue())) / l;
            }
        }

        int best_k1 = 1;
        int best_k2 = 1;
        float best_d = 0;
        for (int k = 1; k <= 255; ++k){
            for (int k2 = k; k2 <= 255; ++k2){
                if (cnt == 1 && k != k2)
                    continue;

                float P1 = 0;
                float P2 = 0;
                float P3 = 0;
                float m1 = 0;
                float m2 = 0;
                float m3 = 0;
                for (int i = 0; i < k; ++i){
                    P1 += p[i];
                    m1 += p[i] * i / l;
                }

                for (int i = k; i < k2; ++i){
                    P2 += p[i];
                    m2 += p[i] * i / l;
                }

                for (int i = k2; i <= 255; ++i) {
                    P3 += p[i];
                    m3 += p[i] * i / l;
                }

                System.out.println("P:" + P1 + " " + P3);
                if (cnt == 2 && (P1 == 0 || P2 == 0 || P3 == 0))
                    continue;

                if (cnt == 1 && (P1 == 0 || P3 == 0))
                    continue;

                P1 /= l;
                P2 /= l;
                P3 /= l;
                m1 /= P1;
                m2 /= P2;
                m3 /= P3;

                if (cnt == 2){
                    float d = ((m1 - global_m) * (m1 - global_m) * P1
                            + (m2 - global_m) * (m2 - global_m) * P2
                            + (m3 - global_m) * (m3 - global_m) * P3);
                    if (d > best_d) {
                        best_k1 = k;
                        best_k2 = k2;
                        best_d = d;
                    }
                }else{
                    float d = ((m1 - global_m) * (m1 - global_m) * P1 + (m3 - global_m) * (m3 - global_m) * P3);
                    if (d > best_d) {
                        best_k1 = k;
                        best_k2 = k;
                        best_d = d;
                    }
                }
            }
        }

        System.out.println("thresholds:" + best_k1 + " " + best_k2);
        threshold1 = gammaConverter.useGamma(best_k1 / 255f);
        threshold2 = gammaConverter.useGamma(best_k2 / 255f);
    }

    @Override
    public Displayable applyFilter(Displayable source) {
        if (isGrayFilter)
            this.grayscaleSource = toGrayscale(source);
        else
            this.grayscaleSource = source.clone();

        calculateThresholds();

        Displayable result = grayscaleSource.clone();

        for (int x = 0; x < grayscaleSource.getWidth(); x++) {
            for (int y = 0; y < grayscaleSource.getHeight(); y++) {
                RgbConvertable resultPixel;
                if (isProcessingNeeded(x, y)) {
                    resultPixel = applyMask(x, y);
                }
                else {
                    resultPixel = grayscaleSource.getPixel(x, y);
                }

                result.setPixel(x, y, resultPixel);
            }
        }

        return normalize(result);
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return List.of(
                new IntegerAlgorithmParameter("Threshold cnt", 1, 2, 1),
                new IntegerAlgorithmParameter("Is monochrome", 0, 1, 0)
        );
    }

    @Override
    protected float applyMaskValue(int i, int j, float sourceValue) {
        if (sourceValue < threshold1) {
            return 0f;
        }
        else if (sourceValue < threshold2) {
            return 0.5f;
        }
        else {
            return 1f;
        }
    }
}
