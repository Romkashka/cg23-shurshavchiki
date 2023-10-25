package ru.shurshavchiki.businessLogic.domain.services;

public class DrawingServiceImpl implements DrawingService {
    private final UserProjectDataHolder dataHolder;

    public DrawingServiceImpl(UserProjectDataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }
}
