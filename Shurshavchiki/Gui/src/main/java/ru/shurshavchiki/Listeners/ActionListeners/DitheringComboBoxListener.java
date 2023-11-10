package ru.shurshavchiki.Listeners.ActionListeners;

import ru.shurshavchiki.Frames.DitherFileFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DitheringComboBoxListener implements ActionListener {

    private JComboBox<String> comboBox;

    private DitherFileFrame frame;

    public DitheringComboBoxListener(DitherFileFrame frame, JComboBox<String> comboBox){
        this.comboBox = comboBox;
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent event) {
        frame.setAlgorithm((String)comboBox.getSelectedItem());
    }
}
