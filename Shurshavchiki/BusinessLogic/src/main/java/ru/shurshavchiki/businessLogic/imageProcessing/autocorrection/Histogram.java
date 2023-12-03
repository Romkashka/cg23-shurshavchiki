package ru.shurshavchiki.businessLogic.imageProcessing.autocorrection;

import java.util.List;

public record Histogram(String ChannelName, List<Integer> ValueDistribution) {
}
