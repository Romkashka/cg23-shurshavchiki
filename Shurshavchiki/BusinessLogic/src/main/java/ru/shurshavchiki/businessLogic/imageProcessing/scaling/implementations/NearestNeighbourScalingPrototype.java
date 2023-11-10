package ru.shurshavchiki.businessLogic.imageProcessing.scaling.implementations;

import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.entities.PnmFile;
import ru.shurshavchiki.businessLogic.domain.models.Header;
import ru.shurshavchiki.businessLogic.domain.models.RgbConvertable;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingParameters;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters.ScalingAlgorithmParameter;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

public class NearestNeighbourScalingPrototype implements ScalingAlgorithm {
    private Displayable source;
    private ScalingParameters scalingParameters;

    double originalPixelWidth;
    double originalPixelHeight;
    double resultPixelWidth;
    double resultPixelHeight;

    @Override
    public void init(List<ScalingAlgorithmParameter> parameters) {}

    @Override
    public List<ScalingAlgorithmParameter> getParametersToInit() {
        return new ArrayList<>();
    }

    @Override
    public Displayable scale(ScalingParameters scalingParameters, Displayable source) {
        this.source = source;
        this.scalingParameters = scalingParameters;
        List<List<RgbConvertable>> newPixels = new ArrayList<>();
        Window window = new Window(0, 1, 0, 1);

        originalPixelWidth = 1.0 / (double) source.getWidth();
        originalPixelHeight = 1.0 / (double) source.getHeight();
        resultPixelWidth = 1.0 / scalingParameters.NewWidth();
        resultPixelHeight = 1.0 / scalingParameters.NewHeight();

        for (int y = 0; y < scalingParameters.NewHeight(); y++) {
            Window currentWindow = new Window(window);
            List<RgbConvertable> currentRow = new ArrayList<>();

            currentWindow = moveWindowDown(currentWindow, getCoordinates(new Point(0, y), scalingParameters.NewWidth(), scalingParameters.NewHeight()).getY());

            for (int x = 0; x < scalingParameters.NewWidth(); x++) {
                Point2D currentPixelCoordinates = getCoordinates(new Point(x, y), scalingParameters.NewWidth(), scalingParameters.NewHeight());
                System.out.println("Current coordinates: " + currentPixelCoordinates);

                currentWindow = moveWindowRight(currentWindow, currentPixelCoordinates.getX());

                System.out.println("x: " + x + ", y: " + y + ", window: " + currentWindow);

                List<Point> pixels = List.of(new Point(currentWindow.Left, currentWindow.Top),
                        new Point(currentWindow.Left, currentWindow.Bottom),
                        new Point(currentWindow.Right, currentWindow.Top),
                        new Point(currentWindow.Right, currentWindow.Bottom)
                );

                List<Point2D> points = new ArrayList<>();

                for (Point point: pixels) {
                    points.add(getCoordinates(point, source.getWidth(), source.getHeight()));
                }

                Point finalPixel = null;
                double min_distance = Double.MAX_VALUE;
                for (int i = 0; i < 4; i++) {
                    double distance = Point2D.distance(currentPixelCoordinates.getX(), currentPixelCoordinates.getY(), points.get(i).getX(), points.get(i).getY());
                    if (distance < min_distance) {
                        min_distance = distance;
                        finalPixel = pixels.get(i);
                    }
                }

                System.out.println("Result: " + source.getPixel(finalPixel.x, finalPixel.y).FloatRed());

                currentRow.add(source.getPixel(finalPixel.x, finalPixel.y));
            }

            newPixels.add(currentRow);
        }

        return new PnmFile(new Header(source.getHeader().getMagicNumber(),
                scalingParameters.NewWidth(),
                scalingParameters.NewHeight(),
                source.getHeader().getMaxValue()), newPixels);
    }

    @Override
    public String getName() {
        return "Nearest Neighbour";
    }

    private Window moveWindowRight(Window window, double currentX) {
        Window result = new Window(window);
        while (getWindowBottomRightCornerCoordinates(result, originalPixelWidth, originalPixelHeight).getX() < currentX) {
            result = new Window(result.Left + 1, result.Right + 1, result.Top, result.Bottom);
            System.out.println("right");
        }

        return result;
    }

    private Window moveWindowDown(Window window, double currentY) {
        Window result = new Window(window);
        while (getWindowBottomRightCornerCoordinates(result, originalPixelWidth, originalPixelHeight).getY() < currentY) {
            result = new Window(result.Left, result.Right, result.Top + 1, result.Bottom + 1);
            System.out.println("down");
        }

        return result;
    }

    private Point2D getCoordinates(Point point, double width, double height) {
        return new Point2D.Double((point.getX() + 0.5) / width, (point.getY() + 0.5) / height);
    }

    private Point2D getWindowBottomRightCornerCoordinates(Window window, double pixelWidth, double pixelHeight) {
        return new Point2D.Double((window.Right + 1) * pixelWidth, (window.Bottom + 1) * pixelHeight);
    }

    record Window(int Left, int Right, int Top, int Bottom) {
        public Window(Window other) {
            this(other.Left, other.Right, other.Top, other.Bottom);
        }

        @Override
        public String toString() {
            return "Window{" +
                    "Left=" + Left +
                    ", Right=" + Right +
                    ", Top=" + Top +
                    ", Bottom=" + Bottom +
                    '}';
        }
    }
}
