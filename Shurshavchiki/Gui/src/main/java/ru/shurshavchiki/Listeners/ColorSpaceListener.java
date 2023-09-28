package ru.shurshavchiki.Listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorSpaceListener extends AbstractAction {
    private static Observer Observer;

    public static class Observer {
        public void setColorSpace(ColorSpaceListener action, String colorSpace){
            action.colorSpace = colorSpace;
            System.out.println(colorSpace);
        };
    }

    private String colorSpace;
    private Observer observer;

    public ColorSpaceListener(String text, String colorSpace, Observer observer) {
        super(text);
        this.colorSpace = colorSpace;
        this.observer = observer;
    }

    public String getColorSpace() {
        return colorSpace;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (observer == null) {
            return;
        }
        observer.setColorSpace(this, getColorSpace());
    }
}
