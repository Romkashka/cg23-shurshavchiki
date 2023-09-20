package ru.shurshavchiki.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ru.shurshavchiki.util.ManualControllerMediator;

import java.io.File;

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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open picture");
        File file = fileChooser.showOpenDialog(stage);
        System.out.println(file.getName());
        System.out.println("Readable: " + file.canRead());
        System.out.println("Writable: " + file.canWrite());
        ManualControllerMediator.getInstance().newFile(file.getName());
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
