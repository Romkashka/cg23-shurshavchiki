package ru.shurshavchiki.businessLogic.domain.deprecatedServices;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.util.ColorSpaceRegistry;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers.LineBaseDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers.LineBaseRepository;
import ru.shurshavchiki.businessLogic.drawing.lineTipDrawers.LineTipDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineTipDrawers.LineTipRepository;
import ru.shurshavchiki.businessLogic.drawing.models.Drawing;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;
import ru.shurshavchiki.businessLogic.gamma.util.GammaConvertersRegistry;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithmRepository;
import ru.shurshavchiki.businessLogic.imageProcessing.filling.ImageCreationAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.filling.ImageCreationAlgorithmRepository;

import java.io.File;

public interface UserProjectDataHolder {
    File getFile();
    Displayable getShownDisplayable();
    GammaConverter getInputGammaConverter();
    GammaConverter getShownGammaConverter();
    ColorSpaceConverter getColorSpaceConverter();
    ChannelChooser getChannelChooser();
    Displayable getStartingDisplayable();
    Displayable getDisplayableWithFilters();
    ColorSpaceConverter getStartingColorSpaceConverter();
    ChannelChooser getStartingChannelChooser();
    ColorSpaceRegistry getColorSpaceRegistry();
    GammaConvertersRegistry getGammaConvertersRegistry();

    LineBaseDrawer getLineBaseDrawer();
    LineTipDrawer getStartLineTipDrawer();
    LineTipDrawer getEndLineTipDrawer();

    DitheringAlgorithm getDitheringAlgorithm();
    ImageCreationAlgorithm getImageCreationAlgorithm();

    LineBaseRepository getLineBaseRepository();
    LineTipRepository getLineTipRepository();
    DitheringAlgorithmRepository getDitheringAlgorithmRepository();
    ImageCreationAlgorithmRepository getImageCreationAlgorithmRepository();


    void setFile(File file);
    void setShownDisplayable(Displayable displayable);
    void setInputGammaConverter(GammaConverter gammaConverter);
    void setShownGammaConverter(GammaConverter gammaConverter);
    void setColorSpaceConverter(ColorSpaceConverter colorSpaceConverter);
    void setChannelChooser(ChannelChooser channelChooser);
    void setStartingDisplayable(Displayable displayable);
    void setDisplayableWithFilters(Displayable displayable);
    void setStartingColorSpaceConverter(ColorSpaceConverter converter);
    void setStartingChannelChooser(ChannelChooser channelChooser);

    void setLineBaseDrawer(LineBaseDrawer lineBaseDrawer);
    void setStartLineTipDrawer(LineTipDrawer lineTipDrawer);
    void setEndLineTipDrawer(LineTipDrawer lineTipDrawer);

    void setDitheringAlgorithm(DitheringAlgorithm ditheringAlgorithm);
    void setImageCreationAlgorithm(ImageCreationAlgorithm imageCreationAlgorithm);

    int addDrawing(Drawing drawing);
    void deleteDrawing(int index);
}
