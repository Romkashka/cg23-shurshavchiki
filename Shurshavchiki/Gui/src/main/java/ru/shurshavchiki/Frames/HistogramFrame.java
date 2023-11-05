package ru.shurshavchiki.Frames;

import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.Helpers.ChannelColorHelper;
import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.Helpers.InputSetHelper;
import ru.shurshavchiki.Listeners.BitRateListener;
import ru.shurshavchiki.Listeners.DitheringComboBoxListener;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.Panels.DrawingPanel;
import ru.shurshavchiki.Panels.HistogramPanel;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class HistogramFrame extends JFrame {

    private List<HistogramPanel> preview = new ArrayList<HistogramPanel>();

    public HistogramFrame(){
        this.setTitle("Color histogram");

        for (String channel : PanelMediator.getInstance().getSettingPanel().getChosenChannels()){
            System.out.println(channel);
            HistogramPanel previewSingle = new HistogramPanel(ChannelColorHelper.map(channel));
            previewSingle.setVisible(true);
            preview.add(previewSingle);
        }

        this.setMinimumSize(new Dimension(preview.size() * 380, 500));
        this.setSize(new Dimension(preview.size() * 380, 500));

        this.setLayout(new GridBagLayout());
        GridBagHelper gridSetter = new GridBagHelper();

        for (HistogramPanel panel : preview){
            gridSetter.alignCenter().nextCell().fillHorizontally().gap(30);
            this.add(panel, gridSetter.get());
        }

        gridSetter.insertEmptyRow(this, 20);
        gridSetter.nextRow().alignLeft();
        var buttonOk = new JButton("OK");
        buttonOk.setPreferredSize(new Dimension(100, 60));
        buttonOk.addActionListener(e -> handleOk());
        this.add(buttonOk, gridSetter.get());

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void handleOk(){
        try {
//            PanelMediator.getInstance().setDisplayableDithered(selectedAlgorithm, selectedBitRate);

            this.dispose();
        }catch (Exception exception){
            new ExceptionHandler().handleException(exception);
        }
    }
}
