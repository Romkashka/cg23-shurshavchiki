package ru.shurshavchiki.Frames;

import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.PanelMediator;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class ColorChooserFrame extends JFrame {

    private JColorChooser colorChooser = new JColorChooser();

    private String operation;

    public ColorChooserFrame(String operation, Color color){
        this.operation = operation;
        this.setTitle("Color selector for " + operation);

        this.setLayout(new GridBagLayout());
        GridBagHelper gridSetter = new GridBagHelper();
        gridSetter.nextCell().fillHorizontally().span();

        this.add(colorChooser);
        setColor(color);

        gridSetter.nextRow().alignLeft().gap(200);
        var buttonOk = new JButton("OK");
        buttonOk.setPreferredSize(new Dimension(100, 60));
        buttonOk.addActionListener(e -> handleOk());
        this.add(buttonOk, gridSetter.get());

        gridSetter.nextCell().alignRight().span();
        var buttonCancel = new JButton("Cancel");
        buttonCancel.setPreferredSize(new Dimension(100, 60));
        buttonCancel.addActionListener(e -> handleCancel());
        this.add(buttonCancel, gridSetter.get());

        this.setPreferredSize(new Dimension(720, 480));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void handleCancel(){
        this.dispose();
    }

    private void handleOk(){
        try {
            if (Objects.equals(operation, "main color"))
                PanelMediator.getInstance().getOneToolPanel().setMainColor(colorChooser.getColor());

            handleCancel();
        }catch (Exception exception){
            new ExceptionHandler().handleException(exception);
        }
    }

    public void setColor(Color color){
        this.colorChooser.setColor(color);
    }
}
