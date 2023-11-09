package ru.shurshavchiki.Listeners.ChangeListeners;

import ru.shurshavchiki.Frames.ExportFileFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BitRateListener implements ChangeListener {

    private String type;

    private ExportFileFrame frame;

    public BitRateListener(ExportFileFrame frame, String type){
        this.type = type;
        this.frame = frame;
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        JSpinner spinner = (JSpinner) e.getSource();
        switch (type){
            case "bit rate":
                frame.setBitRate((int)spinner.getValue());
                break;
            default:
                break;
        }
    }
}
