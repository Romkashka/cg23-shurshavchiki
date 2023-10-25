package ru.shurshavchiki.businessLogic.domain.services;

import lombok.Getter;
import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooser;
import ru.shurshavchiki.businessLogic.colorSpace.channelChoosers.ChannelChooserBuilder;
import ru.shurshavchiki.businessLogic.colorSpace.factories.ColorSpaceFactory;
import ru.shurshavchiki.businessLogic.colorSpace.models.Channel;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.exceptions.ChannelException;
import ru.shurshavchiki.businessLogic.exceptions.ColorSpaceException;
import ru.shurshavchiki.businessLogic.gamma.converters.GammaConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ImageProcessingServiceImpl implements ImageProcessingService {
    private final UserProjectDataHolder dataHolder;

    public ImageProcessingServiceImpl(UserProjectDataHolder dataHolder) {
        this.dataHolder = dataHolder;
    }
}
