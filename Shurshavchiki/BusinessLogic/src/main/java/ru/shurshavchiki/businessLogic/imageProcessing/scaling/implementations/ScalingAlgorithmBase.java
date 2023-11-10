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

public abstract class ScalingAlgorithmBase implements ScalingAlgorithm {
    protected Displayable source;
    protected double kernelRadius;
    protected double sourcePixelWidth;
    protected double sourcePixelHeight;

    protected int maxX;
    protected int minX = 0;
    protected int maxY;
    protected int minY = 0;

    @Override
    public Displayable scale(ScalingParameters scalingParameters, Displayable source) {
        this.source = source;
        List<List<RgbConvertable>> newPixels = new ArrayList<>();

        maxX = source.getWidth();
        maxY = source.getHeight();

        if (scalingParameters.OffsetX() < 0.0) {
            maxX = (int) ((1.0 + scalingParameters.OffsetX()) * source.getWidth());
        }
        else if (scalingParameters.OffsetX() > 0.0) {
            minX = (int) (scalingParameters.OffsetX() * source.getWidth());
        }

        if (scalingParameters.OffsetY() < 0.0) {
            minY = (int) (-scalingParameters.OffsetY() * source.getHeight());
        }
        else if (scalingParameters.OffsetY() > 0.0) {
            maxY = (int) ((1.0 - scalingParameters.OffsetY()) * source.getHeight());
        }

        sourcePixelWidth = 1.0 / (double) (maxX - minX);
        sourcePixelHeight = 1.0 / (double) (maxY - minY);
        for (int y = 0; y < scalingParameters.NewHeight(); y++) {
            List<RgbConvertable> currentRow = new ArrayList<>();
            for (int x = 0; x < scalingParameters.NewWidth(); x++) {
                Point2D coordinates = getCoordinates(new Point(x, y), scalingParameters.NewWidth(), scalingParameters.NewHeight());
                Window window = createWindow(coordinates);
                RgbPixel color = (RgbPixel) calculateColor(coordinates, window);
                System.out.println("x: " + x + ", y: " + y + ", Color=[" + color.FloatRed() + ", " + color.FloatGreen() + ", " + color.FloatBlue() + "]");
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
        System.out.println("Window creation");
        System.out.println("center: " + windowCenter);
        int rightBorder = (int) Math.floor(windowCenter.getX() + kernelRadius - 0.5);
        int leftBorder = (int) Math.ceil(windowCenter.getX() - kernelRadius - 0.5);

        int upperBorder = (int) Math.floor(windowCenter.getY() + kernelRadius - 0.5);
        int bottomBorder = (int) Math.ceil(windowCenter.getY() - kernelRadius - 0.5);

        System.out.println("r: " + rightBorder);
        System.out.println("l: " + leftBorder);
        System.out.println("u: " + upperBorder);
        System.out.println("b: " + bottomBorder);

        List<PositionedPixel> windowPixels = new ArrayList<>();

        for (int x = leftBorder; x <= rightBorder; x++) {
            for (int y = bottomBorder; y <= upperBorder; y++) {
                PositionedPixel currentPixel = new PositionedPixel(
                        source.getPixel(Math.max(minX, Math.min(maxX, x)), Math.max(minY, Math.min(maxY, y))),
                        getPixelSourceCoordinates(x, y)
                );
                System.out.println(currentPixel);

                windowPixels.add(currentPixel);
            }
        }
        System.out.println("window size: " + windowPixels.size());

        return new Window(windowPixels);
    }

    protected abstract RgbConvertable calculateColor(Point2D point, Window window);

    Point2D getPixelSourceCoordinates(int x, int y) {
        return new Point2D.Double((double) x / (maxX - minX) * sourcePixelWidth, (double) y / (maxY - minY) * sourcePixelHeight);
    }

    private Point2D getCoordinates(Point point, double width, double height) {
        return new Point2D.Double((point.getX() + 0.5) / width, (point.getY() + 0.5) / height);
    }

    record Window(List<PositionedPixel> Pixels) {}

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
