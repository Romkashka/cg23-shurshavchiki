package ru.shurshavchiki.businessLogic.imageProcessing.filters;

import ru.shurshavchiki.businessLogic.domain.util.ImplementationRepositoryBase;
import ru.shurshavchiki.businessLogic.imageProcessing.filters.implementations.*;

import java.util.List;

public class ImageFilterRepository extends ImplementationRepositoryBase<ImageFilter> {
    public ImageFilterRepository() {
        super(List.of(
                new ThresholdFilter(),
                new MedianFilter(),
                new GaussianBlurFilter(),
                new BoxBlurFilter(),
                new SobelFilter()
        ));
    }
}
