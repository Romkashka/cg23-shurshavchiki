package ru.shurshavchiki.businessLogic.imageProcessing.dithering;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;

public class MockDitheringAlgorithm implements DitheringAlgorithm {
    @Override
    public String getName() {
        return "Mock";
    }

    @Override
    public Displayable applyDithering(Displayable inputImage) {
        return null;
    }
}
