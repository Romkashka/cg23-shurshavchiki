package ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.businessLogic.exceptions.ScalingException;

public class FloatScalingAlgorithmParameter extends ScalingAlgorithmParameter {
    @Getter
    private final float lowerLimit;
    @Getter
    private final float upperLimit;
    @Getter
    private float value;

    public FloatScalingAlgorithmParameter(float lowerLimit, float upperLimit) {
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    public void setValue(float value) {
        if (value < lowerLimit || value > upperLimit) {
            throw ScalingException.AlgorithmParameterOutOfBorder(getName(), lowerLimit, upperLimit, value);
        }

        this.value = value;
    }
}
