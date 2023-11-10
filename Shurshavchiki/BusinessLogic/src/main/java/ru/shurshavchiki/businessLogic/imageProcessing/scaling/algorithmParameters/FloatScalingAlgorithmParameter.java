package ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters;

import lombok.Getter;
import ru.shurshavchiki.businessLogic.exceptions.ScalingException;

public class FloatScalingAlgorithmParameter extends ScalingAlgorithmParameter {
    @Getter
    private final float lowerLimit;
    @Getter
    private final float upperLimit;
    @Getter
    private float value;

    public FloatScalingAlgorithmParameter(String name, float lowerLimit, float upperLimit) {
        super(name);
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.value = lowerLimit;
    }

    public FloatScalingAlgorithmParameter(String name, float lowerLimit, float upperLimit, float value) {
        super(name);
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
        this.value = value;
    }

    public void setValue(float value) {
        if (value < lowerLimit || value > upperLimit) {
            throw ScalingException.AlgorithmParameterOutOfBorder(getName(), lowerLimit, upperLimit, value);
        }

        this.value = value;
    }

    @Override
    public String toString() {
        return "FloatScalingAlgorithmParameter{" +
                "lowerLimit=" + lowerLimit +
                ", upperLimit=" + upperLimit +
                ", value=" + value +
                '}';
    }
}
