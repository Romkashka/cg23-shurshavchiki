package ru.shurshavchiki.businessLogic.imageProcessing.scaling;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.util.WithName;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters.ScalingAlgorithmParameter;

import java.util.List;

public interface ScalingAlgorithm extends WithName {
    void init(List<ScalingAlgorithmParameter> parameters);
    List<ScalingAlgorithmParameter> getParametersToInit();
    Displayable scale(ScalingParameters scalingParameters, Displayable source);
}
