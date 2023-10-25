package ru.shurshavchiki.businessLogic.domain.services;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.colorSpace.util.ColorSpaceRegistry;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;
import ru.shurshavchiki.businessLogic.gamma.util.GammaConvertersRegistry;

import java.io.File;

public class ProjectDataHolderImpl implements UserProjectDataHolder {
    @Getter @Setter
    private File file;

    @Getter @Setter
    private Displayable startingDisplayable;
    @Getter @Setter
    private Displayable displayableWithFilters;
    @Getter @Setter
    private Displayable shownDisplayable;

    @Getter
    private GammaConvertersRegistry gammaConvertersRegistry;
    @Getter
    private GammaConverter inputGammaConverter;
    @Getter
    private GammaConverter shownGammaConverter;

    @Getter
    private ColorSpaceRegistry colorSpaceRegistry;
    @Getter @Setter
    private ColorSpaceConverter colorSpaceConverter;
    @Getter @Setter
    private ChannelChooser channelChooser;

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
