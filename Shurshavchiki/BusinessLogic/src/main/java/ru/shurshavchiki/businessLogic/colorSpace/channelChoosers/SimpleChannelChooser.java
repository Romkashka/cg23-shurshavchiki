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
    private final List<Double> constants;
    private int chosenChannelsAmount;

    public SimpleChannelChooser(List<Double> constants, List<Integer> channelMask) {
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
    public byte[] apply(byte[] allChannelsData) {
        byte[] result =  new byte[allChannelsData.length * chosenChannelsAmount / MASK_LENGTH];

        for (int i = 0; i < allChannelsData.length; i += MASK_LENGTH) {
            for (int j = 0; j < MASK_LENGTH; j++) {
                result[i + j] = (byte) (allChannelsData[i + j] * channelMask.get(j));
            }
        }

        return result;
    }

    @Override
    public byte[] fillAllChannels(byte[] rawData) {
        byte[] result = new byte[rawData.length * (MASK_LENGTH / chosenChannelsAmount)];

        for (int i = 0; i < rawData.length; i += chosenChannelsAmount) {
            byte[] currentPart = Arrays.copyOfRange(rawData, i, i + chosenChannelsAmount);
            for (int j = 0; j < chosenChannelsAmount; j++) {
                result[i + j] = (byte) (currentPart[j % currentPart.length] * channelMask.get(j) + constants.get(j));
            }
        }

        return result;
    }
}
