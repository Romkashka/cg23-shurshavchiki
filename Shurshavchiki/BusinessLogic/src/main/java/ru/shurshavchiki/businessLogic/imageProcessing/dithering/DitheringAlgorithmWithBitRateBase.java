package ru.shurshavchiki.businessLogic.imageProcessing.dithering;

import lombok.Getter;
import ru.shurshavchiki.businessLogic.exceptions.DitheringException;

public abstract class DitheringAlgorithmWithBitRateBase implements DitheringAlgorithmWithBitRate {
    protected final int BIT_RATE_LOWER_BOUND = 1;
    protected final int BIT_RATE_UPPER_BOUND = 8;
    @Getter
    protected int bitRate;

    @Override
    public int getBitRateLowerBound() {
        return BIT_RATE_LOWER_BOUND;
    }

    @Override
    public int getBitRateUpperBound() {
        return BIT_RATE_UPPER_BOUND;
    }

    @Override
    public void setBitRate(int bitRate) {
        if (bitRate < BIT_RATE_LOWER_BOUND || BIT_RATE_UPPER_BOUND < bitRate) {
            throw DitheringException.IllegalBitRateValue(BIT_RATE_LOWER_BOUND, BIT_RATE_UPPER_BOUND);
        }

        this.bitRate = bitRate;
    }
}
