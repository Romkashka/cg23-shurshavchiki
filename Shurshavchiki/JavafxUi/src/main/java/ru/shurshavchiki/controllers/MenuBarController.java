package ru.shurshavchiki.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.shurshavchiki.util.ManualControllerMediator;

public class MenuBarController {
    private Stage stage;

    @FXML
    public void initialize() {
        ManualControllerMediator.getInstance().setMenuBarController(this);
    }

    public void createNewFile(ActionEvent actionEvent) {
        System.out.println("Creating new file (actually I'm not)");
    }
    public void onFileOpen(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open picture");
        fileChooser.showOpenDialog(stage);
    }

    public void openExistingFile(ActionEvent actionEvent) {
        ManualControllerMediator.getInstance().newFile("Aboba");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open picture");
        fileChooser.showOpenDialog(stage);
    }

    public void closeCurrentFile(ActionEvent actionEvent) {
    }

    public void saveExistingFile(ActionEvent actionEvent) {
    }

    public void saveFileAs(ActionEvent actionEvent) {
    }

    public void quitApplication(ActionEvent actionEvent) {
    }
}
