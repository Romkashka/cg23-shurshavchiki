package ru.shurshavchiki.Listeners.ActionListeners;

import ru.shurshavchiki.Frames.ColorChooserFrame;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.Panels.OneToolPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstrumentChoseListener implements ActionListener {

    private OneToolPanel toolPanel;

    public InstrumentChoseListener(OneToolPanel toolPanel){
        this.toolPanel = toolPanel;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()){
            case "cursor":
                toolPanel.setupCursor();
                PanelMediator.getInstance().getDrawingPanel().clearPreview();
                break;
            case "line":
                toolPanel.setupLine();
                break;
            case "main color":
                new ColorChooserFrame("main color", toolPanel.getMainColor());
                break;
        }
    }
}
