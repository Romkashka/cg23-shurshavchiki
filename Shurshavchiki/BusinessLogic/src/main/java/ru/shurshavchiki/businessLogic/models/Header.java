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
