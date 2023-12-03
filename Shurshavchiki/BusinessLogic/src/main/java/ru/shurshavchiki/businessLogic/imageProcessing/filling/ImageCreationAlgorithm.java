package ru.shurshavchiki.businessLogic.imageProcessing.filling;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.util.WithName;

public interface ImageCreationAlgorithm extends WithName {
    Displayable create(int height, int width);
}
