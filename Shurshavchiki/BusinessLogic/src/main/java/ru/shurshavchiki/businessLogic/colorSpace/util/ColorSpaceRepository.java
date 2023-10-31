package ru.shurshavchiki.businessLogic.colorSpace.util;

import ru.shurshavchiki.businessLogic.colorSpace.factories.*;
import ru.shurshavchiki.businessLogic.domain.util.ImplementationRepositoryBase;

import java.util.List;

public class ColorSpaceRepository extends ImplementationRepositoryBase<ColorSpaceFactory> {
    public ColorSpaceRepository() {
        super(List.of(
                new RGBColorSpaceFactory(),
                new HSLColorSpaceFactory(),
                new HSVColorSpaceFactory(),
                new YCbCr601ColorSpaceFactory(),
                new YCbCr709ColorSpaceFactory(),
                new YCoCgColorSpaceFactory(),
                new CMYColorSpaceFactory()
        ));
    }
}
