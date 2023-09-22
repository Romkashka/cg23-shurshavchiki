package ru.shurshavchiki.controllers;

import javafx.fxml.FXML;
import ru.shurshavchiki.util.ManualControllerMediator;

public class InformationSectionController {
    @FXML
    public void initialize() {
        ManualControllerMediator.getInstance().setInformationSectionController(this);
    }
}
