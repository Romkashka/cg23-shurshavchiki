package ru.shurshavchiki.Frames;

import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.Helpers.InputSetHelper;
import ru.shurshavchiki.Listeners.ChangeListeners.HistogramListener;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.Panels.HistogramPanel;
import ru.shurshavchiki.Panels.ScaleAlgorithmPanel;
import ru.shurshavchiki.businessLogic.domain.util.WithName;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.Histogram;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingParameters;

import javax.swing.*;
import java.awt.*;
import java.security.AlgorithmParameters;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ScaleFrame extends JFrame {

    private int height;

    private int width;

    private JComboBox<String> algorithmBox;

    private ScaleAlgorithmPanel panel;

    private List<String> algorithms;

    private JSpinner spinnerHeight;

    private JSpinner spinnerWidth;

    private JTextPane mainHeightText;

    private JTextPane mainWidthText;

    public ScaleFrame(){
        this.setTitle("Scale picture");

        this.setMinimumSize(new Dimension(600, 600));
        this.setSize(new Dimension(600, 600));

        this.setLayout(new GridBagLayout());
        GridBagHelper gridSetter = new GridBagHelper();

        algorithms = PanelMediator.getInstance().getScaleAlgorithms();
        algorithmBox = new JComboBox<>(algorithms.toArray(new String[0]));
        algorithmBox.setSelectedIndex(0);

        panel = new ScaleAlgorithmPanel();
        panel.createPanel(PanelMediator.getInstance().getScaleAlgorithm(algorithms.get(algorithmBox.getSelectedIndex())));
        panel.setVisible(true);

        SpinnerModel modelWidth = new SpinnerNumberModel(100, 1, 100000, 10);
        spinnerWidth = InputSetHelper.setJSpinner(modelWidth, "create width");
        mainWidthText = InputSetHelper.setJText();
        mainWidthText.setText("New Width:");
        spinnerWidth.setVisible(true);
        mainWidthText.setVisible(true);

        SpinnerModel modelHeight = new SpinnerNumberModel(100, 1, 100000, 10);
        spinnerHeight = InputSetHelper.setJSpinner(modelHeight, "create height");
        mainHeightText = InputSetHelper.setJText();
        mainHeightText.setText("New Height:");
        spinnerHeight.setVisible(true);
        mainHeightText.setVisible(true);

        gridSetter.alignCenter();
        this.add(algorithmBox, gridSetter.get());
        gridSetter.insertEmptyRow(this, 20);
        gridSetter.alignCenter();
        this.add(panel, gridSetter.get());
        gridSetter.insertEmptyRow(this, 20);

        gridSetter.nextRow().nextCell().alignCenter();
        this.add(mainWidthText, gridSetter.get());
        gridSetter.nextCell().alignCenter();
        this.add(spinnerWidth, gridSetter.get());

        gridSetter.nextRow().nextCell().alignCenter();
        this.add(mainHeightText, gridSetter.get());
        gridSetter.nextCell().alignCenter();
        this.add(spinnerHeight, gridSetter.get());

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

    public void changeAlgorithm(int index){
        algorithmBox.setSelectedIndex(index);
        panel.createPanel(PanelMediator.getInstance().getScaleAlgorithm(algorithms.get(algorithmBox.getSelectedIndex())));
    }

    private void handleCancel(){
        this.dispose();
    }

    private void handleOk(){
        try {
            height = (int)spinnerHeight.getValue();
            width = (int)spinnerWidth.getValue();
            var parameters = new ScalingParameters(height, width, 0, 0);
            PanelMediator.getInstance().scaleImage(algorithms.get(algorithmBox.getSelectedIndex()), parameters);
            handleCancel();
        }catch (Exception exception){
            new ExceptionHandler().handleException(exception);
        }
    }
}
