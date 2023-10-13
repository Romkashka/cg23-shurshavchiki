package ru.shurshavchiki.businessLogic.util;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.converters.ColorSpaceConverter;
import ru.shurshavchiki.businessLogic.entities.Displayable;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;

import javax.swing.*;
import java.io.File;

public class ProjectDataHolderImpl implements UserProjectDataHolder {
    @Getter @Setter
    private File file;

    @Getter @Setter
    private Displayable displayable;

    @Getter @Setter
    private GammaConverter gammaConverter;

    @Getter @Setter
    private ColorSpaceConverter colorSpaceConverter;

    @Getter @Setter
    private ChannelChooser channelChooser;

    @Getter @Setter
    private Displayable startingDisplayable;

    @Getter @Setter
    private ColorSpaceConverter startingColorSpaceConverter;

    @Getter @Setter
    private ChannelChooser startingChannelChooser;
}
