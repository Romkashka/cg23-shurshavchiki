package ru.shurshavchiki.businessLogic.gamma.util;

import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;

public interface GammaConvertersRegistry {
    GammaConverter getGammaConverter(float gamma);
}
