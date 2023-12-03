package ru.shurshavchiki.businessLogic.imageProcessing.filters;

import ru.shurshavchiki.businessLogic.domain.util.ImplementationRepositoryBase;

import java.util.List;

public class ImageFilterRepository extends ImplementationRepositoryBase<ImageFilter> {
    public ImageFilterRepository() {
        super(List.of(
                new MockImageFilter()
        ));
    }
}
