package ru.shurshavchiki.businessLogic.imageProcessing.filling;

import ru.shurshavchiki.businessLogic.domain.util.ImplementationRepositoryBase;

import java.util.List;

public class ImageCreationAlgorithmRepository extends ImplementationRepositoryBase<ImageCreationAlgorithm> {
    public ImageCreationAlgorithmRepository() {
        super(List.of(
                new BlankImageCreationAlgorithm(),
                new GradientImageCreationAlgorithm()
        ));
    }
}
