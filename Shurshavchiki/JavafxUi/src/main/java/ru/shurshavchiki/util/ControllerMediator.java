package ru.shurshavchiki.util;

import ru.shurshavchiki.businessLogic.services.FileService;
import ru.shurshavchiki.controllers.*;

public interface ControllerMediator {
    void setMenuBarController(MenuBarController menuBarController);
    void setToolsSectionController(ToolsSectionController toolsSectionController);
    void setPictureSectionController(PictureSectionController pictureSectionController);
    void setInformationSectionController(InformationSectionController informationSectionController);
    void setLowerBarController(LowerBarController lowerBarController);
    void setFileService(FileService fileService);

}
