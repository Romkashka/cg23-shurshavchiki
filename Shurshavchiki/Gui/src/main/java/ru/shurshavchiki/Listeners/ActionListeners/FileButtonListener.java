package ru.shurshavchiki.Listeners.ActionListeners;

import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.Frames.DitherFileFrame;
import ru.shurshavchiki.Frames.ImageCreateFrame;
import ru.shurshavchiki.PanelMediator;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class FileButtonListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            if (!PanelMediator.getInstance().getOneToolPanel().getChosen().equals("Cursor"))
                return;

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
                case "Dither":
                    ditherFile();
                    break;
                case "Close":
                    if (PanelMediator.getInstance().getSomethingChanged())
                        if (!PanelMediator.getInstance().getConfirm())
                            return;

                    PanelMediator.getInstance().closeImage();
                    break;
                case "Exit":
                    if (PanelMediator.getInstance().getSomethingChanged())
                        if (!PanelMediator.getInstance().getConfirm())
                            return;

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

        JFileChooser fileChooser = getFileChooser();
        int result = fileChooser.showOpenDialog(PanelMediator.getInstance().getSettingPanel());

        if (result == JFileChooser.APPROVE_OPTION) {
            PanelMediator.getInstance().closeImage();
            PanelMediator.getInstance().getSettingPanel().setSelectedFile(fileChooser.getSelectedFile());
            PanelMediator.getInstance().openNewImage(PanelMediator.getInstance().getSettingPanel().getSelectedFile());
        }
    }

    private static JFileChooser getFileChooser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File("C:/Users/1/Desktop"));
        fileChooser.addChoosableFileFilter(new FileFilter() {
            public String getDescription() {
                return "P6 Documents (*.ppm)";
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".ppm");
                }
            }
        });

        fileChooser.addChoosableFileFilter(new FileFilter() {
            public String getDescription() {
                return "P5 Documents (*.pgm)";
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".pgm");
                }
            }
        });

        fileChooser.addChoosableFileFilter(new FileFilter() {
            public String getDescription() {
                return "Portable Network Graphic (*.png)";
            }

            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    return f.getName().toLowerCase().endsWith(".png");
                }
            }
        });
        return fileChooser;
    }

    private void saveAsFile() throws IOException {
        JFileChooser fileChooser = getFileChooser();
        int result = fileChooser.showSaveDialog(PanelMediator.getInstance().getSettingPanel());

        if (result == JFileChooser.APPROVE_OPTION) {
            PanelMediator.getInstance().saveAsImage(fileChooser.getSelectedFile());
        }
    }

    public void ditherFile() {
        new DitherFileFrame();
    }
}