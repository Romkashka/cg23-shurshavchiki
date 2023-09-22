package ru.shurshavchiki.util;

import ru.shurshavchiki.controllers.*;

import java.io.File;

public class ManualControllerMediator implements ControllerMediator {
    private MenuBarController menuBarController;
    private ToolsSectionController toolsSectionController;
    private PictureSectionController pictureSectionController;
    private InformationSectionController informationSectionController;
    private LowerBarController lowerBarController;
    @Override
    public void setMenuBarController(MenuBarController menuBarController) {
        this.menuBarController = menuBarController;
    }

    @Override
    public void setToolsSectionController(ToolsSectionController toolsSectionController) {
        this.toolsSectionController = toolsSectionController;
    }

    @Override
    public void setPictureSectionController(PictureSectionController pictureSectionController) {
        this.pictureSectionController = pictureSectionController;
    }

    @Override
    public void setInformationSectionController(InformationSectionController informationSectionController) {
        this.informationSectionController = informationSectionController;
    }

    @Override
    public void setLowerBarController(LowerBarController lowerBarController) {
        this.lowerBarController = lowerBarController;
    }

//    TODO: add communication with service
    public void newFile(File file) {
        lowerBarController.fileOpened(file.getName());

    }

    public static ManualControllerMediator getInstance() {
        return MediatorHolder.INSTANCE;
    }

    private static class MediatorHolder {
        private static final ManualControllerMediator INSTANCE = new ManualControllerMediator();
    }
}
