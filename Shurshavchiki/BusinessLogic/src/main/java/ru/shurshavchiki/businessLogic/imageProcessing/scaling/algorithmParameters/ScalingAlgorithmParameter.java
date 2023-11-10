package ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters;

import lombok.Getter;

public abstract class ScalingAlgorithmParameter {
    @Getter
    private String name;

    public ScalingAlgorithmParameter(String name) {
        this.name = name;
    }
}
