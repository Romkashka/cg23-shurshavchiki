package ru.shurshavchiki.businessLogic.exceptions;

public class LineException extends GeneralPhotoshopException {
    protected LineException(String message) {
        super(message);
    }

    public static LineException NoSuchLineTipDrawer(String tipName) {
        return new LineException("There is no line tip called '" + tipName + "'");
    }

    public static LineException NoSuchLineBaseDrawer(String baseName) {
        return new LineException("There is no line base called '" + baseName + "'");
    }
}
