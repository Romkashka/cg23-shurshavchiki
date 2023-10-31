package ru.shurshavchiki.businessLogic.imageProcessing.dithering;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.util.WithName;

public interface DitheringAlgorithm extends WithName {
    Displayable applyDithering(Displayable inputImage);
}
