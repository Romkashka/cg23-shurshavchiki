package ru.shurshavchiki.Listeners;

import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.Frames.ImageCreateFrame;
import ru.shurshavchiki.PanelMediator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FileButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            switch (event.getActionCommand()){
                case "New":
                    newFile();
                    break;
                case "Open":
                    openFile();
                    break;
                case "Save":
                    saveFile();
                    break;
                case "SaveAs":
                    saveAsFile();
                    break;
                case "Close":
                    PanelMediator.getInstance().closeImage();
                    break;
                case "Exit":
                    PanelMediator.getInstance().exit();
                    break;
            }
        }catch (Exception exception){
            new ExceptionHandler().handleException(exception);
        }
    }

    private void newFile(){
        PanelMediator.getInstance().closeImage();
        if (!PanelMediator.getInstance().getSomethingChanged()){
            ImageCreateFrame imageCreateFrame = new ImageCreateFrame();
        }
    }

    private void openFile() throws IOException {
        PanelMediator.getInstance().closeImage();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:/Users/1/Desktop"));
        int result = fileChooser.showOpenDialog(PanelMediator.getInstance().getSettingPanel());

        if (result == JFileChooser.APPROVE_OPTION) {
            PanelMediator.getInstance().getSettingPanel().setSelectedFile(fileChooser.getSelectedFile());
            PanelMediator.getInstance().openNewImage(PanelMediator.getInstance().getSettingPanel().getSelectedFile());
        }
    }

    private void saveFile() throws IOException {
        PanelMediator.getInstance().saveImage();
    }

    private void saveAsFile() throws IOException {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showSaveDialog(PanelMediator.getInstance().getSettingPanel());

        if (result == JFileChooser.APPROVE_OPTION) {
            PanelMediator.getInstance().getSettingPanel().setSelectedFile(fileChooser.getSelectedFile());
            PanelMediator.getInstance().saveAsImage(PanelMediator.getInstance().getSettingPanel().getSelectedFile());
        }
    }
}