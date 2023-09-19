package ru.shurshavchiki.util;

import ru.shurshavchiki.controllers.*;

public class ManualControllerMediator implements ControllerMediator {
    private MenuBarController menuBarController;
    private ToolsSectionController toolsSectionController;
    private PictureSectionController pictureSectionController;
    private InformationSectionController informaionSectionController;
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
        this.informaionSectionController = informationSectionController;
    }

    @Override
    public void setLowerBarController(LowerBarController lowerBarController) {
        this.lowerBarController = lowerBarController;
    }

//    TODO: change fileName to actual file object
    public void newFile(String fileName) {
        lowerBarController.fileOpened(fileName);
    }

    public static ManualControllerMediator getInstance() {
        return MediatorHolder.INSTANCE;
    }

    private static class MediatorHolder {
        private static final ManualControllerMediator INSTANCE = new ManualControllerMediator();
    }
}
