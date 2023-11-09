package ru.shurshavchiki.Frames;

import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.Helpers.InputSetHelper;
import ru.shurshavchiki.Listeners.ChangeListeners.BitRateListener;
import ru.shurshavchiki.Listeners.ActionListeners.DitheringComboBoxListener;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.Panels.DrawingPanel;

import javax.swing.*;
import java.awt.*;

public class ExportFileFrame extends JFrame {

    private String selectedAlgorithm;

    private int selectedBitRate;

    private JSpinner spinnerBitRate;

    private JTextPane bitRateText;

    private JComboBox<String> ditheringBox;

    private DrawingPanel preview;

    public ExportFileFrame(){
        this.setTitle("Export image");

        String[] ditheringAlgorithms = PanelMediator.getInstance().getDitheringAlgorithms();

        ditheringBox = new JComboBox<>(ditheringAlgorithms);
        ditheringBox.setSelectedIndex(0);
        setAlgorithm((String)ditheringBox.getSelectedItem());
        ditheringBox.addActionListener(new DitheringComboBoxListener(this, ditheringBox));
        ditheringBox.setPreferredSize(new Dimension(80, 30));

        SpinnerModel modelBitRate = new SpinnerNumberModel(4, 1, 8, 1);
        spinnerBitRate = InputSetHelper.setJSpinner(modelBitRate, "bit rate");
        spinnerBitRate.addChangeListener(new BitRateListener(this, "bit rate"));
        setBitRate((int)spinnerBitRate.getValue());
        bitRateText = InputSetHelper.setJText();
        bitRateText.setText("Bit rate:");
        spinnerBitRate.setVisible(true);
        bitRateText.setVisible(true);

        JTextPane ditheringText = InputSetHelper.setJText();
        ditheringText.setText("Dithering algorithm:");
        ditheringText.setVisible(true);

        preview = new DrawingPanel();
        preview.loadImage(PanelMediator.getInstance().getDrawingPanel().getDisplayable());

        this.setLayout(new GridBagLayout());
        GridBagHelper gridSetter = new GridBagHelper();

        gridSetter.alignCenter();
        this.add(ditheringText);
        gridSetter.nextCell().alignCenter();
        this.add(ditheringBox);
        gridSetter.insertEmptyRow(this, 20);

        gridSetter.nextRow().nextCell().alignCenter();
        this.add(bitRateText, gridSetter.get());
        gridSetter.nextCell().alignCenter();
        this.add(spinnerBitRate, gridSetter.get());

        gridSetter.nextRow().nextCell().alignCenter();
        this.add(preview, gridSetter.get());

        gridSetter.insertEmptyRow(this, 20);
        gridSetter.nextRow();
        var buttonOk = new JButton("OK");
        buttonOk.setPreferredSize(new Dimension(100, 60));
        buttonOk.addActionListener(e -> handleOk());
        this.add(buttonOk, gridSetter.get());

        gridSetter.nextCell().nextCell().alignRight().span();
        var buttonCancel = new JButton("Cancel");
        buttonCancel.setPreferredSize(new Dimension(100, 60));
        buttonCancel.addActionListener(e -> handleCancel());
        this.add(buttonCancel, gridSetter.get());

        this.setPreferredSize(new Dimension(600, 400));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void setAlgorithm(String type){
        selectedAlgorithm = type;
        preview.loadImage(PanelMediator.getInstance().getDisplayablePreview(selectedAlgorithm, selectedBitRate));
    }

    public void setBitRate(int bitRate){
        selectedBitRate = bitRate;
        preview.loadImage(PanelMediator.getInstance().getDisplayablePreview(selectedAlgorithm, selectedBitRate));
    }

    private void handleCancel(){
        this.dispose();
    }

    private void handleOk(){
        try {
            PanelMediator.getInstance().setDisplayableDithered(selectedAlgorithm, selectedBitRate);

            handleCancel();
        }catch (Exception exception){
            new ExceptionHandler().handleException(exception);
        }
    }
}
