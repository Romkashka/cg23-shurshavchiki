package ru.shurshavchiki.util;

import ru.shurshavchiki.controllers.*;

public interface ControllerMediator {
    void setMenuBarController(MenuBarController menuBarController);
    void setToolsSectionController(ToolsSectionController toolsSectionController);
    void setPictureSectionController(PictureSectionController pictureSectionController);
    void setInformationSectionController(InformaionSectionController informationSectionController);
    void setLowerBarController(LowerBarController lowerBarController);


}
