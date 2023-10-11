package ru.shurshavchiki.businessLogic.gamma.util;

import ru.shurshavchiki.businessLogic.exceptions.GammaConversionException;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;
import ru.shurshavchiki.businessLogic.gamma.converters.PlainGammaConverter;
import ru.shurshavchiki.businessLogic.gamma.converters.StandardRGBConverter;

public class PlainGammaConvertersRegistry implements GammaConvertersRegistry {
    @Override
    public GammaConverter getGammaConverter(float gamma) {
        if (gamma == 0) {
            return new StandardRGBConverter();
        }
        if (gamma > 0) {
            return new PlainGammaConverter(gamma);
        }

        throw GammaConversionException.InvalidGammaCoefficient(gamma);
    }
}
