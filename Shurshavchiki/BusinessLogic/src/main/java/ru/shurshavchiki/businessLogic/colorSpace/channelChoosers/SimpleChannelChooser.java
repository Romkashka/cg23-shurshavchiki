package ru.shurshavchiki.businessLogic.colorSpace.channelChoosers;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class SimpleChannelChooser implements ChannelChooser {
    private final int MASK_LENGTH = 3;
    @Getter
    private List<Integer> channelMask;
    @Getter
    private final List<Float> constants;
    private int chosenChannelsAmount;

    public SimpleChannelChooser(List<Float> constants, List<Integer> channelMask) {
        this.constants = constants;
        System.out.println("Constants: " + Arrays.toString(constants.toArray()));
        setChannelMask(channelMask);
        System.out.println("chosenChannelsAmount: " + chosenChannelsAmount);
    }

    public void setChannelMask(List<Integer> channelMask) {
        this.channelMask = channelMask;
        this.chosenChannelsAmount = (int) channelMask.stream().filter(num -> num == 1).count();
        System.out.println("Channels mask: " + Arrays.toString(channelMask.toArray()));
    }

    @Override
    public String getMagicNumber() {
        return switch (chosenChannelsAmount) {
            case 1 -> "P5";
            default -> "P6";
        };
    }

    @Override
    public float[] apply(float[] allChannelsData) {
        float[] result =  new float[allChannelsData.length];

        int offset = 0;
        for (int i = 0; i < allChannelsData.length; i += MASK_LENGTH) {
            for (int j = 0; j < MASK_LENGTH; j++) {
                result[offset] = allChannelsData[i + j] * channelMask.get(j) + constants.get(j) * (1 - channelMask.get(j));
                offset++;
            }
        }

        return result;
    }

    @Override
    public float[] fillAllChannels(float[] rawData) {
        float[] result = new float[rawData.length];
        for (int i = 0; i < rawData.length; i += MASK_LENGTH) {
            float[] currentPart = Arrays.copyOfRange(rawData, i, i + MASK_LENGTH);
            for (int j = 0; j < MASK_LENGTH; j++) {
                result[i + j] = currentPart[j] * channelMask.get(j) + constants.get(j) * (1 - channelMask.get(j));
            }
        }

        return result;
    }
}
