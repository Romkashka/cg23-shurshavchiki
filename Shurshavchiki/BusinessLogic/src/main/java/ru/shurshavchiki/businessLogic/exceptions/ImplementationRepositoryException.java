package ru.shurshavchiki.businessLogic.exceptions;

public class ImplementationRepositoryException extends GeneralPhotoshopException {
    protected ImplementationRepositoryException(String message) {
        super(message);
    }

    public static ImplementationRepositoryException NoSuchImplementation(String name) {
        return new ImplementationRepositoryException("There is no implementation called '" + name + "'");
    }
}
