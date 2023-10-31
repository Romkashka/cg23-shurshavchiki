package ru.shurshavchiki.businessLogic.exceptions;

public class DitheringException extends GeneralPhotoshopException {
    protected DitheringException(String message) {
        super(message);
    }

    public static DitheringException IllegalBitRateValue(int lowerBound, int upperBound) {
        return new DitheringException("Bit rate must be from " + lowerBound + " to " + upperBound);
    }

    public static DitheringException BitRateIsNotSupported() {
        return new DitheringException("Chosen dithering algorithm doesn't support different bit rates");
    }
}
