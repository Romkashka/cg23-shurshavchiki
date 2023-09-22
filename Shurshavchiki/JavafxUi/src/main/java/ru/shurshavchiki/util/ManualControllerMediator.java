package ru.shurshavchiki.util;

import ru.shurshavchiki.businessLogic.entities.Displayable;
import ru.shurshavchiki.businessLogic.entities.PnmDisplayable;
import ru.shurshavchiki.businessLogic.services.FileService;
import ru.shurshavchiki.controllers.*;

import java.io.File;
import java.io.IOException;

public class ManualControllerMediator implements ControllerMediator {
    private MenuBarController menuBarController;
    private ToolsSectionController toolsSectionController;
    private PictureSectionController pictureSectionController;
    private InformationSectionController informationSectionController;
    private LowerBarController lowerBarController;
    private FileService fileService;
    @Override
    public void setMenuBarController(MenuBarController menuBarController) {
        this.menuBarController = menuBarController;
    }

    @Override
    public void setFileService(FileService fileService) {
        this.fileService = fileService;
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

    public void fileOpened(File file) throws IOException {
        lowerBarController.fileOpened(file.getName());
        PnmDisplayable pnmDisplayable = fileService.readFile(file);
        pictureSectionController.drawPicture(pnmDisplayable);
    }

    public void safeFileAs(File file) throws IOException {
        fileService.saveFile(pictureSectionController.getPnmDisplayable(), file);
        lowerBarController.fileOpened(file.getName() + " - saved");
    }

    public static ManualControllerMediator getInstance() {
        return MediatorHolder.INSTANCE;
    }

    private static class MediatorHolder {
        private static final ManualControllerMediator INSTANCE = new ManualControllerMediator();
    }
}
