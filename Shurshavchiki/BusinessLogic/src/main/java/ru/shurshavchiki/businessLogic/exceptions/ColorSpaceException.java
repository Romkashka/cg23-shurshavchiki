package ru.shurshavchiki.businessLogic.exceptions;

public class ColorSpaceException extends GeneralPhotoshopException {
    protected ColorSpaceException(String message) {
        super(message);
    }

    public static ColorSpaceException invalidDataLength() {
        return new ColorSpaceException("Length of data in bytes isn't multiple of size of pixel");
    }

    public static ColorSpaceException noSuchColorSpace(String colorSpaceName) {
        return new ColorSpaceException("No color space with name " + colorSpaceName);
    }
}
