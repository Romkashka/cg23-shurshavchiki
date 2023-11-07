package ru.shurshavchiki.businessLogic.api;

import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooserBuilder;
import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.drawing.lineBaseDrawers.LineBaseDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineDrawers.LineDrawer;
import ru.shurshavchiki.businessLogic.drawing.lineTipDrawers.LineTipDrawer;
import ru.shurshavchiki.businessLogic.drawing.models.Line;
import ru.shurshavchiki.businessLogic.exceptions.ChannelException;
import ru.shurshavchiki.businessLogic.exceptions.DitheringException;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.Histogram;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.PlainContrastCorrector;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.dithering.DitheringAlgorithmWithBitRate;

import java.io.File;
import java.util.List;

public class DataHolderAdapter implements Context {
    protected final UserProjectDataHolder dataHolder;

    public DataHolderAdapter(UserProjectDataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }

    @Override
    public UserProjectDataHolder getDataHolder() {
        return dataHolder;
    }

    @Override
    public List<String> getColorSpacesNames() {
        return dataHolder.getColorSpaceRepository().getAllImplementations();
    }

    @Override
    public List<String> getAllLineBases() {
        return dataHolder.getLineBaseRepository().getAllImplementations();
    }

    @Override
    public List<String> getAllLineTips() {
        return dataHolder.getLineTipRepository().getAllImplementations();
    }

    @Override
    public List<String> getAllImageCreationAlgorithms() {
        return dataHolder.getImageCreationAlgorithmRepository().getAllImplementations();
    }

    @Override
    public List<String> getAllDitheringAlgorithms() {
        return dataHolder.getDitheringAlgorithmRepository().getAllImplementations();
    }

    @Override
    public boolean isDitheringAlgorithmWithBitRate(String name) {
        DitheringAlgorithm algorithm = dataHolder.getDitheringAlgorithmRepository().getImplementationByName(name);
        return algorithm instanceof DitheringAlgorithmWithBitRate;
    }

    @Override
    public void setDitheringAlgorithmBitRate(int bitRate) {
        DitheringAlgorithm ditheringAlgorithm = dataHolder.getDitheringAlgorithm();

        if (!(ditheringAlgorithm instanceof DitheringAlgorithmWithBitRate ditheringAlgorithmWithBitRate)) {
            throw DitheringException.BitRateIsNotSupported();
        }

        ditheringAlgorithmWithBitRate.setBitRate(bitRate);
    }

    @Override
    public void setLineBase(String name) {
        dataHolder.setLineBaseDrawer(dataHolder.getLineBaseRepository().getImplementationByName(name));
    }

    @Override
    public void setStartLineTip(String name) {
        dataHolder.setStartLineTipDrawer(dataHolder.getLineTipRepository().getImplementationByName(name));
    }

    @Override
    public void setEndLineTip(String name) {
        dataHolder.setEndLineTipDrawer(dataHolder.getLineTipRepository().getImplementationByName(name));
    }

    @Override
    public void setNewLine(Line line) {
        dataHolder.setNewLine(line);
    }

    @Override
    public void setImageCreationAlgorithm(String name) {
        dataHolder.setImageCreationAlgorithm(dataHolder.getImageCreationAlgorithmRepository().getImplementationByName(name));
    }

    @Override
    public void setNewImageParameters(int height, int width, String magicNumber) {
        dataHolder.setNewImageHeader(new Header(
                magicNumber,
                width,
                height,
                255
        ));
    }

    @Override
    public void setDitheringAlgorithm(String name) {
        dataHolder.setDitheringAlgorithm(dataHolder.getDitheringAlgorithmRepository().getImplementationByName(name));
    }

    @Override
    public void chooseGamma(float gamma) {
        dataHolder.setNewGammaConverter(dataHolder.getGammaConvertersRegistry().getGammaConverter(gamma));
    }

    @Override
    public void chooseChannel(List<String> channelNames) {
        ChannelChooserBuilder builder = dataHolder.getColorSpaceFactory().getChannelChooserBuilder();
        for (String name: channelNames) {
            Channel channel = getChannelFromName(name);
            builder.withChannel(channel);
        }

        dataHolder.setChannelChooser(builder.build());
    }

    @Override
    public void chooseColorSpace(String colorSpaceName) {
        dataHolder.setColorSpaceFactory(dataHolder.getColorSpaceRepository().getImplementationByName(colorSpaceName));
    }

    @Override
    public void setFile(File file) {
        dataHolder.setFile(file);
    }

    @Override
    public boolean isEmpty() {
        return getShownDisplayable() == null;
    }

    @Override
    public Displayable getShownDisplayable() {
        return dataHolder.getShownDisplayable();
    }

    @Override
    public LineBaseDrawer getLineBaseDrawer() {
        return dataHolder.getLineBaseDrawer();
    }

    @Override
    public LineTipDrawer getStartLineTipDrawer() {
        return dataHolder.getStartLineTipDrawer();
    }

    @Override
    public LineTipDrawer getEndLineTipDrawer() {
        return dataHolder.getEndLineTipDrawer();
    }

    @Override
    public GammaConverter getInputGammaConverter() {
        return dataHolder.getInputGammaConverter();
    }

    @Override
    public List<Histogram> getHistograms() {
        return dataHolder.getHistograms();
    }

    @Override
    public void chooseContrastCorrector(float lowerBoundary, float upperBoundary) {
        dataHolder.setContrastCorrector(new PlainContrastCorrector(upperBoundary, lowerBoundary));
    }

    private Channel getChannelFromName(String channelName) {
        for (Channel channel: Channel.values()) {
            if (channel.name().equals(channelName))
                return channel;
        }

        throw ChannelException.noSuchChannel(channelName);
    }
}
