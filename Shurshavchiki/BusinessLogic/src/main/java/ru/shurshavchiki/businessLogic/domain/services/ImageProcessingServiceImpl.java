package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.models.ColorSpace;
import ru.shurshavchiki.businessLogic.colorSpace.models.SingleChannelUnit;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.ContrastCorrector;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.DistributionGenerator;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.Histogram;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.filling.ImageCreationAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingParameters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImageProcessingServiceImpl implements ImageProcessingService {
    @Override
    public Displayable ditherImage(DitheringAlgorithm algorithm, Displayable source) {
        return algorithm.applyDithering(source);
    }

    @Override
    public Displayable createNewImage(ImageCreationAlgorithm algorithm, int height, int width) {
        return algorithm.create(height, width);
    }

    @Override
    public List<Histogram> generateHistograms(List<SingleChannelUnit> singleChannelUnits, ColorSpace colorSpace, ChannelChooser channelChooser) {
        List<Histogram> result = new ArrayList<>();
        DistributionGenerator distributionGenerator = new DistributionGenerator();

        for (SingleChannelUnit unit: singleChannelUnits) {
            if (!colorSpace.usedInAutocorrection().contains(unit.Channel()) ||
                    !channelChooser.getChannels().contains(unit.Channel())) {
                continue;
            }

            List<Integer> values = distributionGenerator.getDistribution(unit.Values());
            result.add(new Histogram(unit.Channel().name(), values));
        }

        return result;
    }

    @Override
    public List<SingleChannelUnit> autocorrectImage(List<SingleChannelUnit> channelUnits, ColorSpace colorSpace, List<Histogram> distributions, ContrastCorrector contrastCorrector) {
        float lowerLimit = contrastCorrector.calculateLowerLimit(distributions);
        float upperLimit = contrastCorrector.calculateUpperLimit(distributions);
        List<SingleChannelUnit> updatedUnits = new ArrayList<>();
        for (int i = 0; i < channelUnits.size(); i++) {
            if (colorSpace.usedInAutocorrection().contains(channelUnits.get(i).Channel()) &&
                    distributions.get(i).ValueDistribution().stream().filter(value -> value > 0f).count() > 1) {
                updatedUnits.add(contrastCorrector.correctContrast(channelUnits.get(i), lowerLimit, upperLimit));
            }
            else {
                updatedUnits.add(channelUnits.get(i));
            }
        }

        return updatedUnits;
    }

    public Displayable scaleImage(Displayable source, ScalingAlgorithm scalingAlgorithm, ScalingParameters scalingParameters) {
        return scalingAlgorithm.scale(scalingParameters, source);
    }
}
