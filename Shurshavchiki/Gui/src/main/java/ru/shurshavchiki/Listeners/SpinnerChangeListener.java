package ru.shurshavchiki.Listeners;

import ru.shurshavchiki.Panels.OneToolPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class SpinnerChangeListener implements ChangeListener {

    private OneToolPanel panel;

    private String type;

    public SpinnerChangeListener(OneToolPanel panel, String type){
        this.panel = panel;
        this.type = type;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        switch (type){
            case "main size":
                panel.setMainSize(((Double)spinner.getValue()).floatValue());
                break;
            case "":
                break;
        }
    }
}
