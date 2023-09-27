package ru.shurshavchiki.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ru.shurshavchiki.util.ManualControllerMediator;

public class LowerBarController {
    @FXML
    private Label leftLabel;
    @FXML
    private Label rightLabel;


    @FXML
    void initialize() {
        leftLabel.setText("No file");
        rightLabel.setText("No tool");
        ManualControllerMediator.getInstance().setLowerBarController(this);
    }
    public void fileOpened(String name) {
        leftLabel.setText(name);
    }
}
