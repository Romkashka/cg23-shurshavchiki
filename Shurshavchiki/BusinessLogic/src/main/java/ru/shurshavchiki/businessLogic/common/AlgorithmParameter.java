package ru.shurshavchiki.businessLogic.common;

import lombok.Getter;

public abstract class AlgorithmParameter {
    @Getter
    private String name;

    public AlgorithmParameter(String name) {
        this.name = name;
    }
}
