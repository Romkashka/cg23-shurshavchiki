package ru.shurshavchiki.businessLogic.exceptions;

public class FilterException extends GeneralPhotoshopException {
    protected FilterException(String message) {
        super(message);
    }

    public static FilterException InvalidParametersList() {
        return new FilterException("Filter can't be initialized(((");
    }
}
