package ru.shurshavchiki.businessLogic.exceptions;

public class ParameterException extends GeneralPhotoshopException {
    protected ParameterException(String message) {
        super(message);
    }

    public static ParameterException AlgorithmParameterOutOfBorder(String name, double lowerLimit, double upperLimit, double found) {
        return new ParameterException("Parameter " + name + " should be from " + lowerLimit + " to " + upperLimit + ", but " + found + " found");
    }

    public static ParameterException AlgorithmParameterOutOfBorder(String name, int lowerLimit, int upperLimit, int found) {
        return new ParameterException("Parameter " + name + " should be from " + lowerLimit + " to " + upperLimit + ", but " + found + " found");
    }
}
