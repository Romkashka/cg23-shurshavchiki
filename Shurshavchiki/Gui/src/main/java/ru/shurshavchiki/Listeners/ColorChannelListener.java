package ru.shurshavchiki.Listeners;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ColorChannelListener extends AbstractAction {

    private static ColorChannelListener.Observer Observer;

    public static class Observer {
        public void setColorChannel(ColorChannelListener action, String colorChannel){
            action.colorChannel = colorChannel;
            System.out.println(colorChannel);
        };
    }

    private String colorChannel;
    private ColorChannelListener.Observer observer;

    public ColorChannelListener(String text, String colorChannel, ColorChannelListener.Observer observer) {
        super(text);
        this.colorChannel = colorChannel;
        this.observer = observer;
    }

    public String getColorChannel() {
        return colorChannel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (observer == null) {
            return;
        }
        observer.setColorChannel(this, getColorChannel());
    }
}
