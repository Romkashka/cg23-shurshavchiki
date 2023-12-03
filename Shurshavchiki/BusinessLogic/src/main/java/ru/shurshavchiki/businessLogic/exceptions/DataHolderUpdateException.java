package ru.shurshavchiki.businessLogic.exceptions;

public class DataHolderUpdateException extends GeneralPhotoshopException {
    protected DataHolderUpdateException(String message) {
        super(message);
    }

    public static DataHolderUpdateException NoImageToUpdate() {
         return new DataHolderUpdateException("Data holder doesn't contain any image");
    }
}
