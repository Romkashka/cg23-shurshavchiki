package ru.shurshavchiki.Listeners.ColorListeners;

import lombok.Getter;
import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.Panels.appPanels.SettingPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorSpaceListener extends JMenuItem {

    public static class Observer implements ActionListener {
        private SettingPanel settingPanel;

        private ColorSpaceListener colorSpaceListener;

        public Observer(SettingPanel settingPanel, ColorSpaceListener colorSpaceListener){
            this.settingPanel = settingPanel;
            this.colorSpaceListener = colorSpaceListener;
        }
        @Override
        public void actionPerformed(ActionEvent event) {
            try {
                if (!settingPanel.getChosenColorSpace().equals(colorSpaceListener.getColorSpace())){
                    settingPanel.changeChosenColorSpace(colorSpaceListener);
                }
            }catch (Exception exception){
                new ExceptionHandler().handleException(exception);
            }
        }
    }

    @Getter
    private String colorSpace;

    public ColorSpaceListener(String text, String colorSpace, SettingPanel settingPanel) {
        super(text);
        this.addActionListener(new ColorSpaceListener.Observer(settingPanel, this));
        this.colorSpace = colorSpace;
        this.setOpaque(true);
        this.setBackground(Color.white);
    }
}
