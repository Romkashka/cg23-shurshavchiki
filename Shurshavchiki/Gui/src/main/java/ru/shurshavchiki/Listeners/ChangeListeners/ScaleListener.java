package ru.shurshavchiki.Listeners.ChangeListeners;

import ru.shurshavchiki.Frames.ColorChooserFrame;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.Panels.OneToolPanel;
import ru.shurshavchiki.Panels.ScaleAlgorithmPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ScaleListener implements ChangeListener {

    private ScaleAlgorithmPanel panel;

    private String name;

    private String type;

    public ScaleListener(ScaleAlgorithmPanel panel, String name, String type){
        this.panel = panel;
        this.name = name;
        this.type = type;
    }

    @Override
    public void stateChanged(ChangeEvent event) {
        JSpinner spinner = (JSpinner) event.getSource();
        switch (type){
            case "int":
                panel.setIntParameterChange(name, (int)spinner.getValue());
                break;
            case "float":
                panel.setFloatParameterChange(name, ((Double)spinner.getValue()).floatValue());
                break;
        }
    }
}
