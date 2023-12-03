package ru.shurshavchiki.businessLogic.imageProcessing.scaling;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.util.WithName;
import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;

import java.util.List;

public interface ScalingAlgorithm extends WithName {
    void init(List<AlgorithmParameter> parameters);
    List<AlgorithmParameter> getParametersToInit();
    Displayable scale(ScalingParameters scalingParameters, Displayable source);
}
