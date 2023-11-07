package ru.shurshavchiki.businessLogic.api;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.factories.ColorSpaceFactory;
import ru.shurshavchiki.businessLogic.colorSpace.util.ColorSpaceRegistry;
import ru.shurshavchiki.businessLogic.colorSpace.util.ColorSpaceRepository;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers.LineBaseDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers.LineBaseRepository;
import ru.shurshavchiki.businessLogic.drawing.lineDrawers.LineDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineTipDrawers.LineTipDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineTipDrawers.LineTipRepository;
import ru.shurshavchiki.businessLogic.drawing.models.Drawing;
import ru.shurshavchiki.businessLogic.drawing.models.Line;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;
import ru.shurshavchiki.businessLogic.gamma.util.GammaConvertersRegistry;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.ContrastCorrector;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.Histogram;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithmRepository;
import ru.shurshavchiki.businessLogic.imageProcessing.filling.ImageCreationAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.filling.ImageCreationAlgorithmRepository;

import java.io.File;
import java.util.List;

public interface UserProjectDataHolder {
    File getFile();
    Displayable getShownDisplayable();
    GammaConverter getInputGammaConverter();
    GammaConverter getShownGammaConverter();
    GammaConverter getNewGammaConverter();
    ColorSpaceFactory getColorSpaceFactory();
    ChannelChooser getChannelChooser();
    Displayable getStartingDisplayable();
    Displayable getDisplayableWithFilters();
    Displayable getDisplayableWithLinearGamma();
    Displayable getDisplayableWithDrawings();
    ColorSpaceConverter getStartingColorSpaceConverter();
    ChannelChooser getStartingChannelChooser();
    ColorSpaceRepository getColorSpaceRepository();
    GammaConvertersRegistry getGammaConvertersRegistry();

    LineBaseDrawer getLineBaseDrawer();
    LineTipDrawer getStartLineTipDrawer();
    LineTipDrawer getEndLineTipDrawer();
    LineDrawer getLineDrawer();
    Line getNewLine();

    DitheringAlgorithm getDitheringAlgorithm();
    ImageCreationAlgorithm getImageCreationAlgorithm();

    LineBaseRepository getLineBaseRepository();
    LineTipRepository getLineTipRepository();
    DitheringAlgorithmRepository getDitheringAlgorithmRepository();

    Header getNewImageHeader();
    Header setNewImageHeader(Header header);
    ImageCreationAlgorithmRepository getImageCreationAlgorithmRepository();

    List<Histogram> getHistograms();
    ContrastCorrector getContrastCorrector();


    void setFile(File file);
    void setShownDisplayable(Displayable displayable);
    void setInputGammaConverter(GammaConverter gammaConverter);
    void setShownGammaConverter(GammaConverter gammaConverter);
    void setNewGammaConverter(GammaConverter gammaConverter);
    void setColorSpaceFactory(ColorSpaceFactory colorSpaceFactory);
    void setChannelChooser(ChannelChooser channelChooser);
    void setStartingDisplayable(Displayable displayable);
    void setDisplayableWithFilters(Displayable displayable);
    void setDisplayableWithLinearGamma(Displayable displayable);
    void setDisplayableWithDrawings(Displayable displayable);
    void setStartingColorSpaceConverter(ColorSpaceConverter converter);
    void setStartingChannelChooser(ChannelChooser channelChooser);

    void setLineBaseDrawer(LineBaseDrawer lineBaseDrawer);
    void setStartLineTipDrawer(LineTipDrawer lineTipDrawer);
    void setEndLineTipDrawer(LineTipDrawer lineTipDrawer);
    void setLineDrawer(LineDrawer lineDrawer);
    void setNewLine(Line newLine);

    void setDitheringAlgorithm(DitheringAlgorithm ditheringAlgorithm);
    void setImageCreationAlgorithm(ImageCreationAlgorithm imageCreationAlgorithm);

    int addDrawing(Drawing drawing);
    void deleteDrawing(int index);
    List<Drawing> getDrawings();

    void setHistograms(List<Histogram> histograms);
    void setContrastCorrector(ContrastCorrector contrastCorrector);
}
