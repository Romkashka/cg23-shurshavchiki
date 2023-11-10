package ru.shurshavchiki.businessLogic.imageProcessing.dithering;

import ru.shurshavchiki.businessLogic.domain.util.ImplementationRepositoryBase;

import java.util.List;

public class DitheringAlgorithmRepository extends ImplementationRepositoryBase<DitheringAlgorithm> {
    public DitheringAlgorithmRepository() {
        super(List.of(
                new OrderedDitheringAlgorithm(),
                new RandomDitheringAlgorithm(),
                new FloydSteinbergDitheringAlgorithm()
        ));
    }
}
