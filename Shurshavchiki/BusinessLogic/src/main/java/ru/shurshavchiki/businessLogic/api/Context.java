package ru.shurshavchiki.businessLogic.api;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers.LineBaseDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineTipDrawers.LineTipDrawer;
import ru.shurshavchiki.businessLogic.drawing.models.Line;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.Histogram;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingParameters;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters.ScalingAlgorithmParameter;

import java.io.File;
import java.util.List;

public interface Context {
    UserProjectDataHolder getDataHolder();

    List<String> getColorSpacesNames();
    List<String> getAllLineBases();
    List<String> getAllLineTips();
    List<String> getAllImageCreationAlgorithms();
    List<String> getAllDitheringAlgorithms();
    boolean isDitheringAlgorithmWithBitRate(String name);
    void setDitheringAlgorithmBitRate(int bitRate);
    void setLineBase(String name);
    void setStartLineTip(String name);
    void setEndLineTip(String name);
    void setNewLine(Line line);
    void setImageCreationAlgorithm(String name);
    default void setNewImageParameters(int height, int width) {
        setNewImageParameters(height, width, "P6");
    }
    void setNewImageParameters(int height, int width, String magicNumber);
    void setDitheringAlgorithm(String name);
    void chooseGamma(float gamma);
    void chooseChannel(List<String> channelNames);
    void chooseColorSpace(String colorSpaceName);
    void setFile(File file);

    boolean isEmpty();

    Displayable getShownDisplayable();
    LineBaseDrawer getLineBaseDrawer();
    LineTipDrawer getStartLineTipDrawer();
    LineTipDrawer getEndLineTipDrawer();
    GammaConverter getInputGammaConverter();

    List<Histogram> getHistograms();
    void chooseContrastCorrector(float lowerBoundary, float upperBoundary);

    ScalingAlgorithm getScalingAlgorithm();
    void setScalingAlgorithm(ScalingAlgorithm scalingAlgorithm, List<ScalingAlgorithmParameter> parameters);
    void setScalingParameters(ScalingParameters scalingParameters);
}
