package exceptions;

public class WriteFileException extends GeneralPhotoshopException {
    protected WriteFileException(String message) {
        super(message);
    }

    public static WriteFileException unsupportedFileVersion(String version) {
        return new WriteFileException(version + " version files are not supported");
    }
}
