package ru.shurshavchiki.util;

import ru.shurshavchiki.controllers.*;

public class ManualControllerMediator implements ControllerMediator {
    private MenuBarController menuBarController;
    private ToolsSectionController toolsSectionController;
    private PictureSectionController pictureSectionController;
    private InformaionSectionController informaionSectionController;
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
    public void setInformationSectionController(InformaionSectionController informationSectionController) {
        this.informaionSectionController = informationSectionController;
    }

    @Override
    public void setLowerBarController(LowerBarController lowerBarController) {
        this.lowerBarController = lowerBarController;
    }
}
