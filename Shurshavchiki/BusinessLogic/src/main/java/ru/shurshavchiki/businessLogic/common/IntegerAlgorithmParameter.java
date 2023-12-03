package ru.shurshavchiki.businessLogic.common;

import lombok.Getter;
import ru.shurshavchiki.businessLogic.exceptions.ParameterException;

public class IntegerAlgorithmParameter extends AlgorithmParameter {
    @Getter
    private final int lowerLimit;
    @Getter
    private final int upperLimit;
    @Getter
    private int value;

    public IntegerAlgorithmParameter(String name, int lowerLimit, int upperLimit) {
        super(name);
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.value = lowerLimit;
    }

    public IntegerAlgorithmParameter(String name, int lowerLimit, int upperLimit, int value) {
        super(name);
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.value = value;
    }

    public void setValue(int value) {
        if (value < lowerLimit || value > upperLimit) {
            throw ParameterException.AlgorithmParameterOutOfBorder(getName(), lowerLimit, upperLimit, value);
        }

        this.value = value;
    }
}
