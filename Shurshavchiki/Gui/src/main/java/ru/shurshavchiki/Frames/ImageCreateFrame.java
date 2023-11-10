package ru.shurshavchiki.Frames;

import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.Helpers.InputSetHelper;
import ru.shurshavchiki.Listeners.ActionListeners.CreateComboBoxListener;
import ru.shurshavchiki.PanelMediator;

import javax.swing.*;
import java.awt.*;

public class ImageCreateFrame extends JFrame {

    private int height;

    private int width;

    private String selectedType;

    private JSpinner spinnerHeight;

    private JSpinner spinnerWidth;

    private JTextPane mainHeightText;

    private JTextPane mainWidthText;

    private JTextPane generateType;

    private JComboBox<String> generateTypesBox;

    public ImageCreateFrame(){
        this.setTitle("Create image");

        generateTypesBox = new JComboBox<>(PanelMediator.getInstance().getGenerateTypes());
        generateTypesBox.setSelectedIndex(0);
        setType((String)generateTypesBox.getSelectedItem());
        generateTypesBox.addActionListener(new CreateComboBoxListener(this, generateTypesBox));
        generateTypesBox.setPreferredSize(new Dimension(80, 30));

        SpinnerModel modelWidth = new SpinnerNumberModel(1000, 1, 100000, 10);
        spinnerWidth = InputSetHelper.setJSpinner(modelWidth, "create width");
        mainWidthText = InputSetHelper.setJText();
        mainWidthText.setText("Width:");
        spinnerWidth.setVisible(true);
        mainWidthText.setVisible(true);

        SpinnerModel modelHeight = new SpinnerNumberModel(500, 1, 100000, 10);
        spinnerHeight = InputSetHelper.setJSpinner(modelHeight, "create height");
        mainHeightText = InputSetHelper.setJText();
        mainHeightText.setText("Height:");
        spinnerHeight.setVisible(true);
        mainHeightText.setVisible(true);

        generateType = InputSetHelper.setJText();
        generateType.setText("Generation type:");
        generateType.setVisible(true);

        this.setLayout(new GridBagLayout());
        GridBagHelper gridSetter = new GridBagHelper();

        gridSetter.alignCenter();
        this.add(generateType);
        gridSetter.nextCell().alignCenter();
        this.add(generateTypesBox);
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

        this.setPreferredSize(new Dimension(300, 260));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    public void setType(String type){
        selectedType = type;
    }

    private void handleCancel(){
        this.dispose();
    }

    private void handleOk(){
        try {
            PanelMediator.getInstance().closeImage();
            height = (int)spinnerHeight.getValue();
            width = (int)spinnerWidth.getValue();
            PanelMediator.getInstance().closeImage();
            PanelMediator.getInstance().createImage(width, height, selectedType);

            handleCancel();
        }catch (Exception exception){
            new ExceptionHandler().handleException(exception);
        }
    }
}
