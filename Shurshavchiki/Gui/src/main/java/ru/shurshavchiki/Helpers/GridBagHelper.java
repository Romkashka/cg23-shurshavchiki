package ru.shurshavchiki.Helpers;

import lombok.NoArgsConstructor;

import javax.swing.*;
import java.awt.*;

@NoArgsConstructor
public class GridBagHelper {
    private int gridx = 0;

    private int gridy = 0;

    private GridBagConstraints constraints = new GridBagConstraints();

    public GridBagConstraints get() {
        return constraints;
    }

    public GridBagHelper nextCell() {
        constraints = new GridBagConstraints();
        constraints.gridx = gridx++;
        constraints.gridy = gridy;

        return this;
    }

    public GridBagHelper nextRow() {
        constraints = new GridBagConstraints();
        gridy++;
        gridx = 0;
        constraints.gridx = 0;
        constraints.gridy = gridy;
        return this;
    }

    public GridBagHelper span() {
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        return this;
    }

    public GridBagHelper fillHorizontally() {
        constraints.fill = GridBagConstraints.HORIZONTAL;
        return this;
    }

    public GridBagHelper gap(int size) {
        constraints.insets.right = size;
        return this;
    }

    public GridBagHelper spanY() {
        constraints.gridheight = GridBagConstraints.REMAINDER;
        return this;
    }


    public GridBagHelper fillBoth() {
        constraints.fill = GridBagConstraints.BOTH;
        return this;
    }

    public GridBagHelper alignLeft() {
        constraints.anchor = GridBagConstraints.LINE_START;
        return this;
    }

    public GridBagHelper alignCenter() {
        constraints.anchor = GridBagConstraints.CENTER;
        return this;
    }

    public GridBagHelper alignRight() {
        constraints.anchor = GridBagConstraints.LINE_END;
        return this;
    }

    public GridBagHelper alignBottom() {
        constraints.anchor = GridBagConstraints.SOUTH;
        return this;
    }

    public GridBagHelper setWeights(float horizontal, float vertical) {
        constraints.weightx = horizontal;
        constraints.weighty = vertical;
        return this;
    }

    public void insertEmptyRow(Container c, int height) {
        Component comp = Box.createVerticalStrut(height);
        nextCell().nextRow().fillHorizontally().span();
        c.add(comp, get());
        nextRow();
    }

    public void insertEmptyFiller(Container c) {
        Component comp = Box.createGlue();
        nextCell().nextRow().fillBoth().span().spanY().setWeights(1.0f, 1.0f);
        c.add(comp, get());
        nextRow();
    }
}