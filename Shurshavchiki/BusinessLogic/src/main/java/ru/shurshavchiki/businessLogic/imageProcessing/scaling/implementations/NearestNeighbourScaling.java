package ru.shurshavchiki.businessLogic.imageProcessing.scaling.implementations;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingParameters;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters.ScalingAlgorithmParameter;

import java.util.ArrayList;
import java.util.List;

public class NearestNeighbourScaling implements ScalingAlgorithm {
    @Override
    public void init(List<ScalingAlgorithmParameter> parameters) {}

    @Override
    public List<ScalingAlgorithmParameter> getParametersToInit() {
        return new ArrayList<>();
    }

    @Override
    public Displayable scale(ScalingParameters scalingParameters, Displayable source) {
        return null;
    }

    @Override
    public String getName() {
        return "Nearest Neighbour";
    }
}
