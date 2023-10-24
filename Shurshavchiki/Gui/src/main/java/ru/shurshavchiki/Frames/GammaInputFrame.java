package ru.shurshavchiki.Frames;

import lombok.Getter;
import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.Panels.GammaInputPanel;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class GammaInputFrame extends JFrame{
    @Getter
    private String operation;

    private String input;

    private ButtonGroup buttonGroup;

    private GammaInputPanel gammaInputPanel1;

    private GammaInputPanel gammaInputPanel2;

    public GammaInputFrame(String operation, float chosenGamma){
        gammaInputPanel1 = new GammaInputPanel("sRGB", false, this, chosenGamma);
        gammaInputPanel2 = new GammaInputPanel("Plain", true, this, chosenGamma);
        buttonGroup = new ButtonGroup();
        buttonGroup.add(gammaInputPanel1.getJRadioButton());
        buttonGroup.add(gammaInputPanel2.getJRadioButton());
        if (chosenGamma == 0) {
            buttonGroup.setSelected(gammaInputPanel1.getJRadioButton().getModel(), true);
            buttonGroup.setSelected(gammaInputPanel2.getJRadioButton().getModel(), false);
        }else{
            buttonGroup.setSelected(gammaInputPanel1.getJRadioButton().getModel(), false);
            buttonGroup.setSelected(gammaInputPanel2.getJRadioButton().getModel(), true);
        }

        this.operation = operation;
        this.setTitle("Enter gamma to " + operation);
        this.setLayout(new GridBagLayout());
        GridBagHelper gridSetter = new GridBagHelper();
        gridSetter.fillHorizontally().span();
        this.add(gammaInputPanel1, gridSetter.get());
        gridSetter.nextRow().fillHorizontally().span();
        this.add(gammaInputPanel2, gridSetter.get());

        gridSetter.nextRow().gap(200);
        var buttonOk = new JButton("OK");
        buttonOk.setPreferredSize(new Dimension(100, 60));
        buttonOk.addActionListener(e -> handleOk());
        this.add(buttonOk, gridSetter.get());

        gridSetter.nextCell().alignRight().span();
        var buttonCancel = new JButton("Cancel");
        buttonCancel.setPreferredSize(new Dimension(100, 60));
        buttonCancel.addActionListener(e -> handleCancel());
        this.add(buttonCancel, gridSetter.get());

        this.setPreferredSize(new Dimension(400, 200));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.input = "0";

        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    private void handleCancel(){
        this.dispose();
    }

    private void handleOk(){
        try {
            if (buttonGroup.isSelected(gammaInputPanel2.getJRadioButton().getModel()))
                gammaInputPanel2.handleEvent();

            if (Objects.equals(operation, "assign"))
                PanelMediator.getInstance().getSettingPanel().handleInputGammaAssign(input);

            if (Objects.equals(operation, "convert"))
                PanelMediator.getInstance().getSettingPanel().handleInputGammaConvert(input);

            handleCancel();
        }catch (Exception exception){
            new ExceptionHandler().handleException(exception);
        }
    }

    public void setInput(String input){
        this.input = input;
    }
}
