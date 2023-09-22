package ru.shurshavchiki.businessLogic.exceptions;

public class WriteFileException extends GeneralPhotoshopException {
    protected WriteFileException(String message) {
        super(message);
    }

    public static WriteFileException unsupportedFileVersion(String version) {
        return new WriteFileException(version + " version files are not supported");
    }

    public static WriteFileException unsupportedFileFormat() {
        return new WriteFileException("Unsupported file format");
    }
}
