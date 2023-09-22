package ru.shurshavchiki.businessLogic.models;

import lombok.Getter;

public class Header {
    @Getter
    private String magicNumber;

    @Getter
    private int width;

    @Getter
    private int height;

    @Getter
    private int maxValue;

    public Header(String magicNumber, int width, int height, int maxValue) {
        this.magicNumber = magicNumber;
        this.width = width;
        this.height = height;
        this.maxValue = maxValue;
    }
}
