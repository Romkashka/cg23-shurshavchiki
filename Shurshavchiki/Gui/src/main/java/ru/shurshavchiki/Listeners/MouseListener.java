package ru.shurshavchiki.Listeners;

import lombok.Getter;
import ru.shurshavchiki.PanelMediator;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MouseListener extends MouseAdapter {

    private boolean firstClick = true;

    @Getter
    private Point startPoint;

    @Override
    public void mouseClicked(MouseEvent event) {
        if (PanelMediator.getInstance().getOneToolPanel().getChosen().equals("Line")){
            Point point = event.getPoint();
            if (firstClick) {
                startPoint = point;
                firstClick = false;
            } else {
                PanelMediator.getInstance().getDrawingPanel().showLinePreview(startPoint, point);
//                PanelMediator.getInstance().getDrawingPanel().completeLinePreview(startPoint, point);
                firstClick = true;
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent event){
        Point point = event.getPoint();
        if(!firstClick){
            System.out.println(point.x);
            PanelMediator.getInstance().getDrawingPanel().showLinePreview(startPoint, point);
        }
    }

}