package ru.shurshavchiki.businessLogic.exceptions;

public class ScalingException extends GeneralPhotoshopException {
    protected ScalingException(String message) {
        super(message);
    }

    public static ScalingException AlgorithmParameterOutOfBorder(String name, float lowerLimit, float upperLimit, float found) {
        return new ScalingException("Parameter " + name + " should be from " + lowerLimit + " to " + upperLimit + ", but " + found + " found");
    }
}
