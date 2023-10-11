package ru.shurshavchiki.Listeners;

import lombok.Getter;
import ru.shurshavchiki.Panels.SettingPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ColorChannelListener extends JMenuItem{

    public static class Observer implements ActionListener {
        private SettingPanel settingPanel;

        private ColorChannelListener colorChannelListener;

        public Observer(SettingPanel settingPanel, ColorChannelListener colorChannelListener){
            this.settingPanel = settingPanel;
            this.colorChannelListener = colorChannelListener;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            settingPanel.changeChosenChannels(colorChannelListener.getColorChannel(), colorChannelListener);
        }
    }

    @Getter
    private String colorChannel;

    public ColorChannelListener(String text, String colorChannel, SettingPanel settingPanel) {
        super(text);
        this.colorChannel = colorChannel;
        this.addActionListener(new Observer(settingPanel, this));
        this.setOpaque(true);
        this.setBackground(Color.GRAY);
    }
}