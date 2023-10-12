package ru.shurshavchiki.businessLogic.colorSpace.channelChoosers;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.businessLogic.exceptions.ChannelException;

import java.util.Arrays;
import java.util.List;

public class SimpleChannelChooser implements ChannelChooser {
    private final int MASK_LENGTH = 3;
    @Getter
    private List<Integer> channelMask;
    private final List<Float> constants;
    private int chosenChannelsAmount;

    public SimpleChannelChooser(List<Float> constants, List<Integer> channelMask) {
        this.constants = constants;
        setChannelMask(channelMask);
    }

    public void setChannelMask(List<Integer> channelMask) {
        this.channelMask = channelMask;
        this.chosenChannelsAmount = (int) channelMask.stream().filter(num -> num == 1).count();
    }

    @Override
    public String getMagicNumber() {
        return switch (chosenChannelsAmount) {
            case 1 -> "P5";
            case MASK_LENGTH -> "P6";
            default -> throw ChannelException.invalidChannelMask();
        };
    }

    @Override
    public float[] apply(float[] allChannelsData) {
        float[] result =  new float[allChannelsData.length * chosenChannelsAmount / MASK_LENGTH];

        int offset = 0;
        for (int i = 0; i < allChannelsData.length; i += MASK_LENGTH) {
            for (int j = 0; j < MASK_LENGTH; j++) {
                if (channelMask.get(j) == 1) {
                    result[offset] = allChannelsData[i + j] * channelMask.get(j);
                    offset++;
                }
            }
        }

        return result;
    }

    @Override
    public float[] fillAllChannels(float[] rawData) {
//        System.out.println(Arrays.toString(rawData));
        float[] result = new float[rawData.length * (MASK_LENGTH / chosenChannelsAmount)];
        for (int i = 0; i < rawData.length; i += chosenChannelsAmount) {
            float[] currentPart = Arrays.copyOfRange(rawData, i, i + chosenChannelsAmount);
            for (int j = 0; j < chosenChannelsAmount; j++) {
                result[i + j] = currentPart[j % currentPart.length] * channelMask.get(j) + constants.get(j);
            }
        }
//        System.out.println(Arrays.toString(result));
        return result;
    }
}
