package ru.shurshavchiki.businessLogic.imageProcessing.scaling;

import ru.shurshavchiki.businessLogic.domain.util.ImplementationRepositoryBase;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.implementations.*;

import java.util.List;

public class ScalingAlgorithmRepository extends ImplementationRepositoryBase<ScalingAlgorithm> {
    public ScalingAlgorithmRepository() {
        super(List.of(
                new NearestNeighbourScalingAlgorithm(),
                new BilinearScalingAlgorithm(),
                new LanczosScalingAlgorithm(),
                new BCSplinesScalingAlgorithm(),
                new BilinearSimpleScalingAlgorithm()
        ));
    }
}
