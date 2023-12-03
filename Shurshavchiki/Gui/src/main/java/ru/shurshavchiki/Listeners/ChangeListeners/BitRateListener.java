package ru.shurshavchiki.Listeners.ChangeListeners;

import ru.shurshavchiki.Frames.DitherFileFrame;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class BitRateListener implements ChangeListener {

    private String type;

    private DitherFileFrame frame;

    public BitRateListener(DitherFileFrame frame, String type){
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
