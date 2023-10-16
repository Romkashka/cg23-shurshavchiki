package ru.shurshavchiki.businessLogic.util;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.entities.Displayable;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;

import java.io.File;

public class ProjectDataHolderImpl implements UserProjectDataHolder {
    @Getter @Setter
    private File file;

    @Getter @Setter
    private Displayable shownDisplayable;

    @Getter
    private GammaConverter inputGammaConverter;

    @Getter
    private GammaConverter shownGammaConverter;

    @Getter @Setter
    private ColorSpaceConverter colorSpaceConverter;

    @Getter @Setter
    private ChannelChooser channelChooser;

    @Getter @Setter
    private Displayable startingDisplayable;

    @Getter @Setter
    private Displayable displayableWithFilters;

    @Getter @Setter
    private ColorSpaceConverter startingColorSpaceConverter;

    @Getter @Setter
    private ChannelChooser startingChannelChooser;

    @Override
    public void setInputGammaConverter(GammaConverter inputGammaConverter) {
        this.inputGammaConverter = inputGammaConverter;
        System.out.println("New input gamma converter: " + inputGammaConverter.getName());
    }

    @Override
    public void setShownGammaConverter(GammaConverter shownGammaConverter) {
        this.shownGammaConverter = shownGammaConverter;
        System.out.println("New shown gamma converter: " + shownGammaConverter.getName());
    }
}
