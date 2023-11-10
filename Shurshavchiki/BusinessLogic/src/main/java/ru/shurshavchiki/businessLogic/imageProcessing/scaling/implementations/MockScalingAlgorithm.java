package ru.shurshavchiki.businessLogic.imageProcessing.scaling.implementations;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingParameters;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters.FloatScalingAlgorithmParameter;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters.ScalingAlgorithmParameter;

import java.util.List;

public class MockScalingAlgorithm implements ScalingAlgorithm {
    @Override
    public String getName() {
        return "Mock";
    }

    @Override
    public void init(List<ScalingAlgorithmParameter> parameters) {
        for (var parameter: parameters) {
            if (parameter instanceof FloatScalingAlgorithmParameter floatScalingAlgorithmParameter)
                System.out.println(floatScalingAlgorithmParameter);
        }
    }

    @Override
    public List<ScalingAlgorithmParameter> getParametersToInit() {
        return List.of(new FloatScalingAlgorithmParameter("Aboba", 0f, 1f),
                new FloatScalingAlgorithmParameter("Abobus", 0f, 1f, 0.5f));
    }

    @Override
    public Displayable scale(ScalingParameters scalingParameters, Displayable source) {
        return source;
    }
}
