package ru.shurshavchiki.businessLogic.domain.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ImageDataHolder {
    @Getter
    private Header header;
    @Getter
    private float[] data;
}
