package ru.shurshavchiki.businessLogic.imageProcessing.dithering;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;

public class MockDitheringAlgorithm extends DitheringAlgorithmBase {
    @Override
    public String getName() {
        return "Mock";
    }

    @Override
    public Displayable applyDithering(Displayable inputImage) {
        return null;
    }
}
