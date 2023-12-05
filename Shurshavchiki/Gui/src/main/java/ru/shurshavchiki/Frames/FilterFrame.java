package ru.shurshavchiki.Frames;

import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.Panels.FilterAlgorithmPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class FilterFrame extends JFrame {

    private JComboBox<String> algorithmBox;

    private FilterAlgorithmPanel panel;

    private List<String> algorithms;

    public FilterFrame(){
        this.setTitle("Filter picture");

        this.setMinimumSize(new Dimension(600, 600));
        this.setSize(new Dimension(600, 600));

        this.setLayout(new GridBagLayout());
        GridBagHelper gridSetter = new GridBagHelper();

        algorithms = PanelMediator.getInstance().getFilterAlgorithms();
        algorithmBox = new JComboBox<>(algorithms.toArray(new String[0]));
        algorithmBox.setSelectedIndex(0);
        algorithmBox.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeAlgorithm(algorithmBox.getSelectedIndex());
            }
        });

        panel = new FilterAlgorithmPanel();
        panel.createPanel(PanelMediator.getInstance().getFilterAlgorithm(algorithms.get(algorithmBox.getSelectedIndex())));
        panel.setVisible(true);


        gridSetter.alignCenter();
        this.add(algorithmBox, gridSetter.get());
        gridSetter.insertEmptyRow(this, 20);
        gridSetter.alignCenter();
        this.add(panel, gridSetter.get());
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
        panel.createPanel(PanelMediator.getInstance().getFilterAlgorithm(algorithms.get(algorithmBox.getSelectedIndex())));
        panel.setVisible(true);
        this.validate();
        super.paintComponents(this.getGraphics());
    }

    private void handleCancel(){
        this.dispose();
    }

    private void handleOk(){
        try {
            var algorithmParameters = panel.getParameterValues();
            PanelMediator.getInstance().filterImage(algorithms.get(algorithmBox.getSelectedIndex()), algorithmParameters);
            handleCancel();
        }catch (Exception exception){
            new ExceptionHandler().handleException(exception);
        }
    }
}
