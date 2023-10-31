package ru.shurshavchiki.businessLogic.imageProcessing.dithering;

public interface DitheringAlgorithmWithBitRate extends DitheringAlgorithm {
    int getBitRateLowerBound();
    int getBitRateUpperBound();
    void setBitRate(int bitRate);
    int getBitRate();
}
