package ru.shurshavchiki.businessLogic.api;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.factories.ColorSpaceFactory;
import ru.shurshavchiki.businessLogic.colorSpace.util.ColorSpaceRepository;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers.LineBaseDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers.LineBaseRepository;
import ru.shurshavchiki.businessLogic.drawing.lineDrawers.LineDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineDrawers.SimpleLineDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineTipDrawers.LineTipDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineTipDrawers.LineTipRepository;
import ru.shurshavchiki.businessLogic.drawing.models.Drawing;
import ru.shurshavchiki.businessLogic.drawing.models.Line;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;
import ru.shurshavchiki.businessLogic.gamma.util.GammaConvertersRegistry;
import ru.shurshavchiki.businessLogic.gamma.util.PlainGammaConvertersRegistry;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithmRepository;
import ru.shurshavchiki.businessLogic.imageProcessing.filling.ImageCreationAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.filling.ImageCreationAlgorithmRepository;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserProjectDataHolderImpl implements UserProjectDataHolder {
    @Getter @Setter
    private File file;

    @Getter @Setter
    private Displayable startingDisplayable;
    @Getter @Setter
    private Displayable displayableWithFilters;
    @Getter @Setter
    private Displayable displayableWithLinearGamma;
    @Getter @Setter
    private Displayable shownDisplayable;

    @Getter
    private GammaConvertersRegistry gammaConvertersRegistry;
    @Getter @Setter
    private GammaConverter inputGammaConverter;
    @Getter @Setter
    private GammaConverter shownGammaConverter;
    @Getter @Setter
    private GammaConverter newGammaConverter;

    @Getter
    private final ColorSpaceRepository colorSpaceRepository;
    @Getter @Setter
    private ColorSpaceFactory colorSpaceFactory;
    @Getter @Setter
    private ChannelChooser channelChooser;

    @Getter @Setter
    private ColorSpaceConverter startingColorSpaceConverter;

    @Getter @Setter
    private ChannelChooser startingChannelChooser;

    @Getter @Setter
    private LineDrawer lineDrawer;
    @Getter
    private final LineBaseRepository lineBaseRepository;
    @Getter
    private final LineTipRepository lineTipRepository;
    @Getter @Setter
    private Line newLine;

    @Getter
    private final DitheringAlgorithmRepository ditheringAlgorithmRepository;
    @Getter @Setter
    private DitheringAlgorithm ditheringAlgorithm;

    @Getter
    private final ImageCreationAlgorithmRepository imageCreationAlgorithmRepository;
    @Getter
    private Header newImageHeader;
    @Getter @Setter
    ImageCreationAlgorithm imageCreationAlgorithm;

    private final List<Drawing> drawings;

    public UserProjectDataHolderImpl() {
        this.gammaConvertersRegistry = new PlainGammaConvertersRegistry();
        this.colorSpaceRepository = new ColorSpaceRepository();
        this.ditheringAlgorithmRepository = new DitheringAlgorithmRepository();
        this.imageCreationAlgorithmRepository = new ImageCreationAlgorithmRepository();
        this.lineBaseRepository = new LineBaseRepository();
        this.lineTipRepository = new LineTipRepository();
        this.lineDrawer = new SimpleLineDrawer();
        setLineBaseDrawer(lineBaseRepository.getImplementationByName(lineBaseRepository.getAllImplementations().get(0)));
        setStartLineTipDrawer(lineTipRepository.getImplementationByName(lineTipRepository.getAllImplementations().get(0)));
        setEndLineTipDrawer(lineTipRepository.getImplementationByName(lineTipRepository.getAllImplementations().get(0)));

        drawings = new ArrayList<>();
    }

    @Override
    public LineBaseDrawer getLineBaseDrawer() {
        return lineDrawer.getLineBaseDrawer();
    }

    @Override
    public LineTipDrawer getStartLineTipDrawer() {
        return lineDrawer.getStartLineTipDrawer();
    }

    @Override
    public LineTipDrawer getEndLineTipDrawer() {
        return lineDrawer.getEndLineTipDrawer();
    }

    @Override
    public Header setNewImageHeader(Header header) {
        return this.newImageHeader = header;
    }

    @Override
    public void setLineBaseDrawer(LineBaseDrawer lineBaseDrawer) {
        lineDrawer.setLineBaseDrawer(lineBaseDrawer);
    }

    @Override
    public void setStartLineTipDrawer(LineTipDrawer lineTipDrawer) {
        lineDrawer.setStartLineTipDrawer(lineTipDrawer);
    }

    @Override
    public void setEndLineTipDrawer(LineTipDrawer lineTipDrawer) {
        lineDrawer.setEndLineTipDrawer(lineTipDrawer);
    }

    @Override
    public int addDrawing(Drawing drawing) {
        drawings.add(drawing);
        return drawings.size()-1;
    }

    @Override
    public void deleteDrawing(int index) {
        drawings.remove(index);
    }
}
