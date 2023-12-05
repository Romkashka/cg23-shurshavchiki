package ru.shurshavchiki.businessLogic.imageProcessing.filters;

import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.util.WithName;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;

import java.util.List;

public interface ImageFilter extends WithName {
    void init(List<AlgorithmParameter> parameterList);
    List<AlgorithmParameter> getAlgorithmParameters();
    Displayable applyFilter(Displayable source);
    void setGammaConverter(GammaConverter gammaConverter);
}
