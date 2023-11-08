package ru.shurshavchiki.businessLogic.imageProcessing.scaling;

import ru.shurshavchiki.businessLogic.domain.util.ImplementationRepositoryBase;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.implementations.NearestNeighbourScaling;

import java.util.List;

public class ScalingAlgorithmRepository extends ImplementationRepositoryBase<ScalingAlgorithm> {
    public ScalingAlgorithmRepository() {
        super(List.of(
                new NearestNeighbourScaling()
        ));
    }
}
