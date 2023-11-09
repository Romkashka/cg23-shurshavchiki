package ru.shurshavchiki.Frames;

import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.Helpers.ChannelColorHelper;
import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.Helpers.InputSetHelper;
import ru.shurshavchiki.Listeners.HistogramListener;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.Panels.HistogramPanel;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.Histogram;

import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;

public class HistogramFrame extends JFrame {

    private List<HistogramPanel> preview = new ArrayList<>();

    private float leftChange;

    private JSpinner spinnerLeftChange;

    private JTextPane generateLeftChange;

    private float rightChange;

    private JSpinner spinnerRightChange;

    private JTextPane generateRightChange;

    public HistogramFrame(){
        this.setTitle("Color histogram");

        Integer maxValue = 0;
        for (Histogram histogram : PanelMediator.getInstance().getHistogramsInfo()){
            Integer value = histogram.ValueDistribution().stream().max(Integer::compare).orElseThrow();
            if (value > maxValue)
                maxValue = value;
        }

        for (Histogram histogram : PanelMediator.getInstance().getHistogramsInfo()){
            HistogramPanel previewSingle = new HistogramPanel(histogram, maxValue);
            previewSingle.setVisible(true);
            preview.add(previewSingle);
        }

        SpinnerModel modelLeft = new SpinnerNumberModel(0, 0, 0.5, 0.05);
        spinnerLeftChange = new JSpinner(modelLeft);
        spinnerLeftChange.setPreferredSize(new Dimension(48, 28));
        spinnerLeftChange.addChangeListener(new HistogramListener(this, "left"));
        spinnerLeftChange.setOpaque(false);
        spinnerLeftChange.setVisible(true);

        generateLeftChange = InputSetHelper.setJText();
        generateLeftChange.setText("Lower bound:");
        generateLeftChange.setVisible(true);

        SpinnerModel modelRight = new SpinnerNumberModel(0, 0, 0.5, 0.05);
        spinnerRightChange = new JSpinner(modelRight);
        spinnerRightChange.setPreferredSize(new Dimension(48, 28));
        spinnerRightChange.addChangeListener(new HistogramListener(this, "right"));
        spinnerRightChange.setOpaque(false);
        spinnerRightChange.setVisible(true);

        generateRightChange = InputSetHelper.setJText();
        generateRightChange.setText("Upper bound:");
        generateRightChange.setVisible(true);

        this.setMinimumSize(new Dimension(preview.size() * 380, 500));
        this.setSize(new Dimension(preview.size() * 380, 500));

        this.setLayout(new GridBagLayout());
        GridBagHelper gridSetter = new GridBagHelper();

        for (HistogramPanel panel : preview){
            gridSetter.alignCenter().nextCell().fillHorizontally().gap(30);
            this.add(panel, gridSetter.get());
        }

        gridSetter.insertEmptyRow(this, 20);

        gridSetter.nextCell().alignCenter();
        this.add(generateLeftChange, gridSetter.get());
        gridSetter.nextCell().alignLeft();
        this.add(spinnerLeftChange, gridSetter.get());

        gridSetter.nextRow().nextCell().alignCenter();
        this.add(generateRightChange, gridSetter.get());
        gridSetter.nextCell().alignLeft();
        this.add(spinnerRightChange, gridSetter.get());

        gridSetter.insertEmptyRow(this, 20);
        gridSetter.nextRow().alignLeft();
        var buttonOk = new JButton("OK");
        buttonOk.setPreferredSize(new Dimension(100, 60));
        buttonOk.addActionListener(e -> handleOk());
        this.add(buttonOk, gridSetter.get());
        gridSetter.nextCell().nextCell().nextCell().alignRight();
        var buttonCancel = new JButton("Cancel");
        buttonCancel.setPreferredSize(new Dimension(100, 60));
        buttonCancel.addActionListener(e -> handleCancel());
        this.add(buttonCancel, gridSetter.get());

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void setLeftChange(float value){
        leftChange = value;
    }

    public void setRightChange(float value){
        rightChange = value;
    }

    private void handleCancel(){
        this.dispose();
    }

    private void handleOk(){
        try {
            PanelMediator.getInstance().makeAutocorrection(leftChange, rightChange);
            handleCancel();
        }catch (Exception exception){
            new ExceptionHandler().handleException(exception);
        }
    }
}
