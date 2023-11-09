package ru.shurshavchiki.Listeners.ChangeListeners;

import ru.shurshavchiki.Frames.HistogramFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class HistogramListener implements ChangeListener {

    private String type;

    private HistogramFrame frame;

    public HistogramListener(HistogramFrame frame, String type){
        this.type = type;
        this.frame = frame;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        switch (type){
            case "left":
                frame.setLeftChange(((Double)spinner.getValue()).floatValue());
                break;
            case "right":
                frame.setRightChange(((Double)spinner.getValue()).floatValue());
                break;
            default:
                break;
        }
    }
}
