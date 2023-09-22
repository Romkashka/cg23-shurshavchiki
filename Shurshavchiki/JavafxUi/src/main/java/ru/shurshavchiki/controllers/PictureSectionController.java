package ru.shurshavchiki.controllers;

import javafx.fxml.FXML;
import ru.shurshavchiki.util.ManualControllerMediator;

public class PictureSectionController {
    @FXML
    public void initialize() {
        ManualControllerMediator.getInstance().setPictureSectionController(this);
    }
}
