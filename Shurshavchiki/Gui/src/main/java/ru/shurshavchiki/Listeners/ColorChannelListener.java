package ru.shurshavchiki.Listeners;

import ru.shurshavchiki.Panels.SettingPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class ColorChannelListener extends AbstractAction {

    private static Observer Observer;

    public static class Observer {
        private SettingPanel settingPanel;

        public Observer(SettingPanel settingPanel){
            this.settingPanel = settingPanel;
        }

        public void setColorChannel(ColorChannelListener action, String colorChannel){
            if (!settingPanel.getChosenChannels().equals(colorChannel)){
                settingPanel.changeChosenChannels(colorChannel);
                action.colorChannel = colorChannel;
                System.out.println(colorChannel);
            }
        };
    }

    private String colorChannel;
    private Observer observer;

    public ColorChannelListener(String text, String colorChannel, Observer observer) {
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
