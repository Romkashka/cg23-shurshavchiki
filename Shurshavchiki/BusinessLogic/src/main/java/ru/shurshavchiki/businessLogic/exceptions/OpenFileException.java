package ru.shurshavchiki.businessLogic.exceptions;

public class OpenFileException extends GeneralPhotoshopException {
    protected OpenFileException(String message) {
        super(message);
    }

    public static OpenFileException fileIsCorrupted(String fileName) {
        return new OpenFileException("File " + fileName + " can't be opened!");
    }

    public static OpenFileException notAFile(String fileName) {
        return new OpenFileException(fileName + "is not a file!");
    }

    public static OpenFileException fileCantBeRead() {
        return new OpenFileException("File can't be read!");
    }

    public static OpenFileException unsupportedFileVersion(String version) {
        return new OpenFileException(version + " version files are not supported!");
    }

    public static OpenFileException invalidPngHeader() {
        return new OpenFileException("File is corrupted: png header is invalid");
    }
}
