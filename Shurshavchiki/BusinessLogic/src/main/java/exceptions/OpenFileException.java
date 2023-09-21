package exceptions;

public class OpenFileException extends GeneralPhotoshopException {
    protected OpenFileException(String message) {
        super(message);
    }

    public static OpenFileException fileIsCorrupted(String fileName) {
        return new OpenFileException("File " + fileName + " can't be opened!");
    }

    public static OpenFileException fileCantBeRead(String fileName) {
        return new OpenFileException("File " + fileName + " can't be read!");
    }
}
