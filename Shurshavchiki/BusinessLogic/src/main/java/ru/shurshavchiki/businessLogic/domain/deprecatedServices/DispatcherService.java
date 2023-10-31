package ru.shurshavchiki.businessLogic.domain.deprecatedServices;

import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface DispatcherService {
    UserProjectDataHolder getUserProjectDataHolder();
    void createNewImage(int height, int width);
    void readFile(File file) throws IOException;
    void saveCurrentFile(File file) throws IOException;
    void chooseChannel(List<String> channelNames);
    void chooseColorSpace(String colorSpaceName);
    void assignGamma(float gamma);
    void convertGamma(float gamma);
    void drawLine(RgbConvertable color, float width, Point2D start, Point2D end);
    void ditherImage(File destination) throws IOException;

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
    void setImageCreationAlgorithm(String name);
    void setDitheringAlgorithm(String name);
}
