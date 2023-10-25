package ru.shurshavchiki.businessLogic.domain.services;

import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;

import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface DispatcherService {
    UserProjectDataHolder getUserProjectDataHolder();
    void readFile(File file) throws IOException;
    void saveCurrentFile(File file) throws IOException;
    List<String> getColorSpacesNames();
    void chooseChannel(List<String> channelNames);
    void chooseColorSpace(String colorSpaceName);
    void assignGamma(float gamma);
    void convertGamma(float gamma);
    void drawLine(RgbConvertable color, float width, Point2D start, Point2D end);
    List<String> getAllLineBases();
    List<String> getAllLineTips();
    void setLineBase(String name);
    void setStartLineTip(String name);
    void setEndLineTip(String name);
}
