package ru.shurshavchiki.Helpers;

import ru.shurshavchiki.Listeners.ChangeListeners.SpinnerChangeListener;

import javax.swing.*;
import java.awt.*;

public class InputSetHelper {

    public static JSpinner setJSpinner(SpinnerModel spinnerModel, String type){
        JSpinner spinner = new JSpinner(spinnerModel);
        spinner.setPreferredSize(new Dimension(48, 28));
        spinner.setVisible(false);
        spinner.setOpaque(false);
        spinner.addChangeListener(new SpinnerChangeListener(type));
        return spinner;
    }

    public static JTextPane setJText(){
        JTextPane text = new JTextPane();
        text.setEditable(false);
        text.setVisible(false);
        text.setOpaque(false);
        text.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 18));
        return text;
    }
}
