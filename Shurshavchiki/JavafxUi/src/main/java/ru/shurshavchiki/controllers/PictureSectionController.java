package ru.shurshavchiki.controllers;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelBuffer;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;
import ru.shurshavchiki.businessLogic.entities.PnmDisplayable;
import ru.shurshavchiki.businessLogic.models.RgbConvertable;
import ru.shurshavchiki.util.ManualControllerMediator;

public class PictureSectionController {
    @FXML
    Canvas mainCanvas;

    private PnmDisplayable pnmDisplayable;
    @FXML
    public void initialize() {
        ManualControllerMediator.getInstance().setPictureSectionController(this);
    }

    public void drawPicture(PnmDisplayable pnmDisplayable) {
        this.pnmDisplayable = pnmDisplayable;
        mainCanvas.setHeight(pnmDisplayable.getHeight());
        mainCanvas.setWidth(pnmDisplayable.getWidth());

        GraphicsContext graphicsContext = mainCanvas.getGraphicsContext2D();
        PixelWriter pixelWriter = graphicsContext.getPixelWriter();
        int maxval = pnmDisplayable.getMaxval();
        for (int x = 0; x < pnmDisplayable.getWidth(); x++) {
            for (int y = 0; y < pnmDisplayable.getHeight(); y++) {
                RgbConvertable pixel = pnmDisplayable.getPixel(x, y);
                pixelWriter.setColor(x, y, new Color((double) pixel.Red() / maxval, (double) pixel.Green() / maxval, (double) pixel.Blue() / maxval, 1));
            }
        }

        System.out.println("finished");
    }

    public PnmDisplayable getPnmDisplayable() {
        return pnmDisplayable;
    }
}
