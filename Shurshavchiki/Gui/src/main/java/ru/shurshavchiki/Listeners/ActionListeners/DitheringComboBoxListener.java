package ru.shurshavchiki.Listeners.ActionListeners;

import ru.shurshavchiki.Frames.ExportFileFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DitheringComboBoxListener implements ActionListener {

    private JComboBox<String> comboBox;

    private ExportFileFrame frame;

    public DitheringComboBoxListener(ExportFileFrame frame, JComboBox<String> comboBox){
        this.comboBox = comboBox;
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent event) {
        frame.setAlgorithm((String)comboBox.getSelectedItem());
    }
}
