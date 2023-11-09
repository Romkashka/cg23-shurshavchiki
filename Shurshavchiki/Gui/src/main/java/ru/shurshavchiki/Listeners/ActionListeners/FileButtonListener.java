package ru.shurshavchiki.Listeners.ActionListeners;

import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.Frames.ExportFileFrame;
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
                    PanelMediator.getInstance().saveImage();
                    break;
                case "SaveAs":
                    saveAsFile();
                    break;
                case "Export":
                    exportFile();
                    break;
                case "Close":
                    if (PanelMediator.getInstance().getSomethingChanged())
                        if (!PanelMediator.getInstance().getConfirm())
                            return;
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
        if (PanelMediator.getInstance().getSomethingChanged())
            if (!PanelMediator.getInstance().getConfirm())
                return;

        new ImageCreateFrame();
    }

    private void openFile() throws IOException {
        if (PanelMediator.getInstance().getSomethingChanged())
            if (!PanelMediator.getInstance().getConfirm())
                return;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:/Users/1/Desktop"));
        int result = fileChooser.showOpenDialog(PanelMediator.getInstance().getSettingPanel());

        if (result == JFileChooser.APPROVE_OPTION) {
            PanelMediator.getInstance().closeImage();
            PanelMediator.getInstance().getSettingPanel().setSelectedFile(fileChooser.getSelectedFile());
            PanelMediator.getInstance().openNewImage(PanelMediator.getInstance().getSettingPanel().getSelectedFile());
        }
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

    public void exportFile() throws IOException {
        new ExportFileFrame();
    }
}