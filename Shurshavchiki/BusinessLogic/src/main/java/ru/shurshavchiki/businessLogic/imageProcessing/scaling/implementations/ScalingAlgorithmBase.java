package ru.shurshavchiki.businessLogic.imageProcessing.scaling.implementations;

import com.sun.security.jgss.GSSUtil;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingParameters;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.max;

public abstract class ScalingAlgorithmBase implements ScalingAlgorithm {
    protected Displayable source;
    protected double kernelRadius;
    protected double sourcePixelWidth;
    protected double sourcePixelHeight;

    protected double windowPixelWidth;

    protected double windowPixelHeight;

    protected double leftWindowBound = 0;
    protected double rightWindowBound = 1;
    protected double topWindowBound = 1;
    protected double bottomWindowBound = 0;

    protected int maxX;
    protected int minX = 0;
    protected int maxY;
    protected int minY = 0;


    @Override
    public Displayable scale(ScalingParameters scalingParameters, Displayable source) {
        this.source = source;
        List<List<RgbConvertable>> newPixels = new ArrayList<>();

        minX = 0;
        minY = 0;
        maxX = source.getWidth() - 1;
        maxY = source.getHeight() - 1;

        leftWindowBound = 0;
        rightWindowBound = 1;
        bottomWindowBound = 0;
        topWindowBound = 1;

        rightWindowBound += scalingParameters.OffsetX();
        leftWindowBound += scalingParameters.OffsetX();
        bottomWindowBound += scalingParameters.OffsetY();
        topWindowBound += scalingParameters.OffsetY();

        sourcePixelWidth = 1.0 / (double) (maxX - minX + 1);
        sourcePixelHeight = 1.0 / (double) (maxY - minY + 1);

        windowPixelWidth = max(sourcePixelWidth, 1.0 / (double) (scalingParameters.NewWidth()));
        windowPixelHeight = max(sourcePixelHeight, 1.0 / (double) (scalingParameters.NewHeight()));

        for (int y = 0; y < scalingParameters.NewHeight(); y++) {
            List<RgbConvertable> currentRow = new ArrayList<>();
            for (int x = 0; x < scalingParameters.NewWidth(); x++) {
                Point2D coordinates = getCoordinates(new Point(x, y), scalingParameters.NewWidth(), scalingParameters.NewHeight());
                Window window = createWindow(coordinates);
                RgbPixel color = (RgbPixel) calculateColor(coordinates, window);
                currentRow.add(color);
            }

            newPixels.add(currentRow);
        }

        return new PnmFile(new Header(source.getHeader().getMagicNumber(),
                scalingParameters.NewWidth(),
                scalingParameters.NewHeight(),
                source.getHeader().getMaxValue()), newPixels);
    }

    protected Window createWindow(Point2D windowCenter) {
        int rightBorder = (int) Math.floor(windowCenter.getX() / sourcePixelWidth + kernelRadius * windowPixelWidth / sourcePixelWidth - 0.5);
        int leftBorder = (int) Math.ceil(windowCenter.getX() / sourcePixelWidth - kernelRadius * windowPixelWidth / sourcePixelWidth - 0.5);

        int upperBorder = (int) Math.floor(windowCenter.getY() / sourcePixelHeight + kernelRadius * windowPixelHeight / sourcePixelHeight - 0.5);
        int bottomBorder = (int) Math.ceil(windowCenter.getY() / sourcePixelHeight - kernelRadius * windowPixelHeight / sourcePixelHeight - 0.5);

        List<List<PositionedPixel>> windowPixels = new ArrayList<>();

        for (int y = bottomBorder; y <= upperBorder; y++) {
            List<PositionedPixel> row = new ArrayList<>();
            for (int x = leftBorder; x <= rightBorder; x++) {
                PositionedPixel currentPixel = new PositionedPixel(
                        source.getPixel(max(minX, Math.min(maxX, x)), max(minY, Math.min(maxY, y))),
                        getPixelSourceCoordinates(x, y)
                );

                row.add(currentPixel);
            }
            windowPixels.add(row);
        }

        return new Window(windowPixels);
    }

    protected abstract RgbConvertable calculateColor(Point2D point, Window window);

    Point2D getPixelSourceCoordinates(int x, int y) {
        return new Point2D.Double((double)(x + 0.5) * sourcePixelWidth, (double) (y + 0.5) * sourcePixelHeight);
    }

    private Point2D getCoordinates(Point point, double width, double height) {
        return new Point2D.Double((point.getX() + 0.5) / width * (rightWindowBound - leftWindowBound) + leftWindowBound,
                (point.getY() + 0.5) / height * (topWindowBound - bottomWindowBound) + bottomWindowBound);
    }

    record Window(List<List<PositionedPixel>> Pixels) {}

    record PositionedPixel(RgbConvertable Color, Point2D coordinates) {
        @Override
        public String toString() {
            return "PositionedPixel{" +
                    "Color=[" + Color.FloatRed() + ", " + Color.FloatGreen() + ", " + Color.FloatBlue() + "]" +
                    ", coordinates=" + coordinates +
                    '}';
        }
    }
}
