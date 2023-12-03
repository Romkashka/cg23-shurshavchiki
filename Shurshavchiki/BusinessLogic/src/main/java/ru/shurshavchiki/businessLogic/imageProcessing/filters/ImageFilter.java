package ru.shurshavchiki.businessLogic.imageProcessing.filters;

import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.util.WithName;

import java.util.List;

public interface ImageFilter extends WithName {
    void init(List<AlgorithmParameter> parameterList);
    List<AlgorithmParameter> getAlgorithmParameters();
    Displayable applyFilter(Displayable source);
}
