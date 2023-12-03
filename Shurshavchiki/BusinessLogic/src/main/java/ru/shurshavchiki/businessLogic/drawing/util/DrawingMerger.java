package ru.shurshavchiki.businessLogic.drawing.util;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.drawing.models.Drawing;
import ru.shurshavchiki.businessLogic.drawing.models.FigureOverlap;
import ru.shurshavchiki.businessLogic.drawing.models.PixelOverlap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrawingMerger {
    public Displayable addDrawing(Displayable source, Drawing drawing){
        FigureOverlap overlapped = drawing.getFigureOverlap();
        Header header = source.getHeader();
        List<List<RgbConvertable>> oldPixels = source.getAllPixels();
        for (PixelOverlap pixel : overlapped.getPixelOverlaps()){
            if (0 <= pixel.X() && pixel.X() <= source.getWidth() - 1 && 0 <= pixel.Y() && pixel.Y() <= source.getHeight() - 1) {
                RgbConvertable originalPixel = source.getPixel(pixel.X(), pixel.Y());
                float newRed = originalPixel.FloatRed() * (1 - pixel.Percentage()) + drawing.color().FloatRed() * pixel.Percentage();
                float newGreen = originalPixel.FloatGreen() * (1 - pixel.Percentage()) + drawing.color().FloatGreen() * pixel.Percentage();
                float newBlue = originalPixel.FloatBlue() * (1 - pixel.Percentage()) + drawing.color().FloatBlue() * pixel.Percentage();
                oldPixels.get(pixel.Y()).set(pixel.X(), new RgbPixel(newRed, newGreen, newBlue));
            }
        }
        Displayable newDisplayable = new PnmFile(header, oldPixels);
        return newDisplayable;
        //throw new UnsupportedOperationException("Somebody will do it eventually (DrawingMerger)");
    }
}
