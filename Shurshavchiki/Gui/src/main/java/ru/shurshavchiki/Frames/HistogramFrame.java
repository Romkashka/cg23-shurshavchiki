package ru.shurshavchiki.Frames;

import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.Helpers.InputSetHelper;
import ru.shurshavchiki.Listeners.BitRateListener;
import ru.shurshavchiki.Listeners.DitheringComboBoxListener;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.Panels.DrawingPanel;
import ru.shurshavchiki.Panels.HistogramPanel;

import javax.swing.*;
import java.awt.*;

public class HistogramFrame extends JFrame {

    private HistogramPanel preview;

    public HistogramFrame(){
        this.setTitle("Color histogram");
        this.setMinimumSize(new Dimension(750, 500));

        preview = new HistogramPanel();
        preview.setVisible(true);
        preview.setBackground(Color.RED);

        this.setLayout(new GridBagLayout());
        GridBagHelper gridSetter = new GridBagHelper();

        gridSetter.alignCenter().fillHorizontally();
        this.add(preview, gridSetter.get());

        gridSetter.insertEmptyRow(this, 20);
        gridSetter.nextRow().alignLeft();
        var buttonOk = new JButton("OK");
        buttonOk.setPreferredSize(new Dimension(100, 60));
        buttonOk.addActionListener(e -> handleOk());
        this.add(buttonOk, gridSetter.get());

        this.setPreferredSize(new Dimension(600, 400));
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
