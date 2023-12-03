package ru.shurshavchiki.businessLogic.colorSpace.util;

import lombok.Getter;
import lombok.NonNull;
import ru.shurshavchiki.businessLogic.colorSpace.factories.*;
import ru.shurshavchiki.businessLogic.exceptions.ColorSpaceException;

import java.util.List;

public class ColorSpaceRegistry {
    @Getter
    private final List<ColorSpaceFactory> factories = List.of(
            new RGBColorSpaceFactory(),
            new HSLColorSpaceFactory(),
            new HSVColorSpaceFactory(),
            new YCbCr601ColorSpaceFactory(),
            new YCbCr709ColorSpaceFactory(),
            new YCoCgColorSpaceFactory(),
            new CMYColorSpaceFactory()
    );

    public ColorSpaceFactory getFactoryByName(@NonNull String colorSpaceName) {
        for (ColorSpaceFactory factory: factories) {
            if (factory.getName().equals(colorSpaceName)) {
                return factory;
            }
        }

        throw ColorSpaceException.noSuchColorSpace(colorSpaceName);
    }
}
