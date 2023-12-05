package ru.shurshavchiki.businessLogic.imageProcessing.filters;

import ru.shurshavchiki.businessLogic.domain.util.ImplementationRepositoryBase;
import ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations.GaussianBlurFilter;
import ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations.MedianFilter;
import ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations.ThresholdFilter;

import java.util.List;

public class ImageFilterRepository extends ImplementationRepositoryBase<ImageFilter> {
    public ImageFilterRepository() {
        super(List.of(
                new ThresholdFilter(),
                new MedianFilter(),
                new GaussianBlurFilter()
        ));
    }
}
