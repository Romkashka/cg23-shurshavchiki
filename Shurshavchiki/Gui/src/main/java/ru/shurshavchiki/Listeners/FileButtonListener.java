package ru.shurshavchiki.Listeners;

import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.PanelMediator;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class FileButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()){
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
    }

    private void openFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:/Users/1/Desktop"));
        int result = fileChooser.showOpenDialog(PanelMediator.getInstance().getSettingPanel());

        if (result == JFileChooser.APPROVE_OPTION) {
            PanelMediator.getInstance().getSettingPanel().setSelectedFile(fileChooser.getSelectedFile());
            try {
                PanelMediator.getInstance().openNewImage(PanelMediator.getInstance().getSettingPanel().getSelectedFile());
            } catch (Exception e) {
                new ExceptionHandler().handleException(e);
            }
        }
    }

    private void saveFile(){
        try {
            PanelMediator.getInstance().saveImage(PanelMediator.getInstance().getSettingPanel().getSelectedFile());
        } catch (Exception e) {
            new ExceptionHandler().handleException(e);
        }
    }

    private void saveAsFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showSaveDialog(PanelMediator.getInstance().getSettingPanel());

        if (result == JFileChooser.APPROVE_OPTION) {
            PanelMediator.getInstance().getSettingPanel().setSelectedFile(fileChooser.getSelectedFile());
            try {
                PanelMediator.getInstance().saveAsImage(PanelMediator.getInstance().getSettingPanel().getSelectedFile());
            } catch (Exception e) {
                new ExceptionHandler().handleException(e);
            }
        }
    }
}