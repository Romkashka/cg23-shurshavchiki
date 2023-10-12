package ru.shurshavchiki.Panels;

import lombok.Getter;
import ru.shurshavchiki.Frames.GammaInputFrame;

import javax.swing.*;
import java.awt.*;

public class GammaInputPanel extends JPanel {
    @Getter
    private JRadioButton jRadioButton;

    private JTextField jTextField;

    private GammaInputFrame frame;

    public GammaInputPanel(String radioButtonName, Boolean hasTextField, GammaInputFrame frame) {
        jRadioButton = new JRadioButton(radioButtonName);
        this.setLayout(new FlowLayout());
        this.add(jRadioButton);
        this.frame = frame;
        if (hasTextField){
            jTextField = new JTextField(10);
            this.add(jTextField);
        }

        jRadioButton.addActionListener(e -> handleEvent());
    }

    public void handleEvent() {
        if (jTextField != null)
            frame.setInput(jTextField.getText());
        else
            frame.setInput("0");
    }
}