package ru.shurshavchiki.businessLogic.imageProcessing.filters;

import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.FloatAlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.IntegerAlgorithmParameter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;

import java.util.List;

public class MockImageFilter extends ImageFilterBase {
    @Override
    public String getName() {
        return "Mock";
    }

    @Override
    public void init(List<AlgorithmParameter> parameterList) {
        for (var parameter: parameterList) {
            if (parameter instanceof IntegerAlgorithmParameter integerAlgorithmParameter) {
                System.out.println("Parameter: " + integerAlgorithmParameter.getName() + ", value: " + integerAlgorithmParameter.getValue());
            }
            else if (parameter instanceof FloatAlgorithmParameter floatAlgorithmParameter) {
                System.out.println("Parameter: " + floatAlgorithmParameter.getName() + ", value: " + floatAlgorithmParameter.getValue());
            }
        }

        mask = new float[][] {{1f}};
        maskRadius = 0;
        coefficient = 1f;
    }

    @Override
    public List<AlgorithmParameter> getAlgorithmParameters() {
        return List.of(
                new FloatAlgorithmParameter("Float parameter 1", 0.0f, 1.0f),
                new IntegerAlgorithmParameter("Integer parameter 1", 0, 255)
        );
    }
}
