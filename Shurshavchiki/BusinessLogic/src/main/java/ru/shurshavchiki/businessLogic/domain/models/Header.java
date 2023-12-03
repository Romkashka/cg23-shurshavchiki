package ru.shurshavchiki.businessLogic.domain.models;

import lombok.Getter;

public class Header {
    @Getter
    private final String magicNumber;

    @Getter
    private final int width;

    @Getter
    private final int height;

    @Getter
    private final int maxValue;

    public Header(String magicNumber, int width, int height, int maxValue) {
        this.magicNumber = magicNumber;
        this.width = width;
        this.height = height;
        this.maxValue = maxValue;
    }

    public Header(Header other) {
        this.magicNumber = other.magicNumber;
        this.width = other.width;
        this.height = other.height;
        this.maxValue = other.maxValue;
    }

    @Override
    public String toString() {
        return "Header{" +
                "magicNumber='" + magicNumber + '\'' +
                ", width=" + width +
                ", height=" + height +
                ", maxValue=" + maxValue +
                '}';
    }
}
