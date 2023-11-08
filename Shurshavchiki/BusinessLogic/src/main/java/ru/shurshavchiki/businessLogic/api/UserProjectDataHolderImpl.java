package ru.shurshavchiki.businessLogic.api;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.factories.ColorSpaceFactory;
import ru.shurshavchiki.businessLogic.colorSpace.util.ColorSpaceRepository;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
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
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.ContrastCorrector;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.Histogram;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithmRepository;
import ru.shurshavchiki.businessLogic.imageProcessing.filling.ImageCreationAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.filling.ImageCreationAlgorithmRepository;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingAlgorithmRepository;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingParameters;

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
    private Displayable displayableWithDrawings;
    @Getter @Setter
    private Displayable shownDisplayable;

    @Getter
    private final GammaConvertersRegistry gammaConvertersRegistry;
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
    private boolean colorSpaceChanged;
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
    @Getter
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

    @Getter
    private final List<Drawing> drawings;

    @Getter @Setter
    private List<Histogram> histograms;
    @Getter @Setter
    private ContrastCorrector contrastCorrector;

    @Getter
    private final ScalingAlgorithmRepository scalingAlgorithmRepository;
    @Getter @Setter
    private ScalingAlgorithm scalingAlgorithm;
    @Getter @Setter
    private ScalingParameters scalingParameters;

    public UserProjectDataHolderImpl() {
        this.gammaConvertersRegistry = new PlainGammaConvertersRegistry();
        this.colorSpaceRepository = new ColorSpaceRepository();
        this.ditheringAlgorithmRepository = new DitheringAlgorithmRepository();
        this.imageCreationAlgorithmRepository = new ImageCreationAlgorithmRepository();
        this.lineBaseRepository = new LineBaseRepository();
        this.lineTipRepository = new LineTipRepository();
        this.scalingAlgorithmRepository = new ScalingAlgorithmRepository();

        this.lineDrawer = new SimpleLineDrawer();
        setLineBaseDrawer(lineBaseRepository.getImplementationByName(lineBaseRepository.getAllImplementations().get(0)));
        setStartLineTipDrawer(lineTipRepository.getImplementationByName(lineTipRepository.getAllImplementations().get(0)));
        setEndLineTipDrawer(lineTipRepository.getImplementationByName(lineTipRepository.getAllImplementations().get(0)));

        setScalingAlgorithm(scalingAlgorithmRepository.getImplementationByName(scalingAlgorithmRepository.getAllImplementations().get(0)));

        drawings = new ArrayList<>();
    }

    @Override
    public GammaConverter getDefaultGammaConverter() {
        return getGammaConvertersRegistry().getGammaConverter(0);
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
    public void setNewLine(Line newLine) {
        GammaConverter gammaConverter = getDefaultGammaConverter();
        RgbConvertable linearColor = new RgbPixel(
                gammaConverter.useGamma(this.inputGammaConverter.correctGamma(newLine.color().FloatRed())),
                gammaConverter.useGamma(this.inputGammaConverter.correctGamma(newLine.color().FloatGreen())),
                gammaConverter.useGamma(this.inputGammaConverter.correctGamma(newLine.color().FloatBlue()))
        );
        this.newLine = new Line(newLine.start(), newLine.end(), newLine.width(), linearColor);
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
