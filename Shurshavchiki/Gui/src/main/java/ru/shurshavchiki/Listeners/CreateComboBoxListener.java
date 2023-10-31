package ru.shurshavchiki.Listeners;

import ru.shurshavchiki.Frames.ImageCreateFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateComboBoxListener implements ActionListener {

    private JComboBox<String> comboBox;

    private ImageCreateFrame frame;

    public CreateComboBoxListener(ImageCreateFrame frame, JComboBox<String> comboBox){
        this.comboBox = comboBox;
        this.frame = frame;
    }

    public void actionPerformed(ActionEvent event) {
        frame.setType((String)comboBox.getSelectedItem());
    }
}
