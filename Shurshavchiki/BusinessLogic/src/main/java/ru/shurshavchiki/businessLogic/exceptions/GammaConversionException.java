package ru.shurshavchiki.businessLogic.exceptions;

public class GammaConversionException extends GeneralPhotoshopException {
    protected GammaConversionException(String message) {
        super(message);
    }

    public static GammaConversionException InvalidGammaCoefficient(float value) {
        return new GammaConversionException("Gamma coefficient must be positive, but " + value + "was found");
    }
}
