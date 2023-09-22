package ru.shurshavchiki.controllers;

import javafx.fxml.FXML;
import ru.shurshavchiki.util.ManualControllerMediator;

public class ToolsSectionController {
    @FXML
    public void initialize() {
        ManualControllerMediator.getInstance().setToolsSectionController(this);
    }
}
