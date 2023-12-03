package ru.shurshavchiki.Listeners.ChangeListeners;

import ru.shurshavchiki.PanelMediator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class SpinnerChangeListener implements ChangeListener {

    private String type;

    public SpinnerChangeListener(String type){
        this.type = type;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        switch (type){
            case "main size":
                PanelMediator.getInstance().getOneToolPanel().setMainSize(((Double)spinner.getValue()).floatValue());
                break;
            default:
                break;
        }
    }
}
