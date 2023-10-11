package ru.shurshavchiki.Listeners;

import ru.shurshavchiki.Panels.SettingPanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorSpaceListener extends AbstractAction {

    public static class Observer {
        private SettingPanel settingPanel;

        public Observer(SettingPanel settingPanel){
            this.settingPanel = settingPanel;
        }

        public void setColorSpace(ColorSpaceListener action, String colorSpace){
            if (!settingPanel.getChosenColorSpace().equals(colorSpace)){
                settingPanel.changeChosenColorSpace(colorSpace);
                action.colorSpace = colorSpace;
            }
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
