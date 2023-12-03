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

    public static WriteFileException noDisplayable() {
        return new WriteFileException("No image to save");
    }

    public static WriteFileException noFile() {
        return new WriteFileException("Destination isn't chosen");
    }

    public static WriteFileException criticalChunkIsAbsent(String chunkType) {
        return new WriteFileException(chunkType + "chunk wasn't found!");
    }
}
