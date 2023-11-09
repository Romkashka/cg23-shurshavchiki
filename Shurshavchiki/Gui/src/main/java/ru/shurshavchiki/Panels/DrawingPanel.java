package ru.shurshavchiki.Panels;

import lombok.Getter;
import ru.shurshavchiki.Listeners.MouseListener;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class DrawingPanel extends JPanel {

	private BufferedImage image = null;

	private JLabel imageLabel;

	@Getter
	private Displayable displayable = null;

	private BufferedImage preview = null;

	private MouseListener mouseListener = new MouseListener();

	public DrawingPanel(){
		this.addMouseListener(mouseListener);
		this.addMouseMotionListener(mouseListener);
	}

	public void loadImage(Displayable displayable) {
		this.displayable = displayable;
		this.castToBuffer();
		this.paint(getGraphics());
	}

	public void castToBuffer() {
		image = new BufferedImage(displayable.getWidth(), displayable.getHeight(), TYPE_INT_RGB);
		for (int x = 0; x < this.displayable.getWidth(); x++) {
			for (int y = 0; y < this.displayable.getHeight(); y++) {
				var pixel = this.displayable.getPixel(x, y);
				image.setRGB(x, y, new Color(pixel.Red(), pixel.Green(), pixel.Blue()).getRGB());
			}
		}
	}

	@Override
	public Dimension getPreferredSize(){
		if (image == null)
			return new Dimension();

		return new Dimension(image.getWidth(), image.getHeight());
	}

	public void closeImage(){
		displayable = null;
		image = null;
		paintComponent(getGraphics());
	}

	public void completeLinePreview(Point startPoint, Point endPoint){
		preview = null;
		PanelMediator.getInstance().drawLine(startPoint, endPoint);
		paintComponent(getGraphics());
	}

	public static BufferedImage deepCopy(BufferedImage bi) {
		ColorModel cm = bi.getColorModel();
		boolean isAlphaPreMultiplied = cm.isAlphaPremultiplied();
		WritableRaster raster = bi.copyData(bi.getRaster().createCompatibleWritableRaster());
		return new BufferedImage(cm, raster, isAlphaPreMultiplied, null);
	}

	public void showLinePreview(Point startPoint, Point endPoint){
		if (displayable != null && endPoint.x < image.getWidth() && endPoint.y < image.getHeight()){
			preview = deepCopy(image);
			int rgb = PanelMediator.getInstance().getOneToolPanel().getMainColor().getRGB();
			float size = PanelMediator.getInstance().getOneToolPanel().getMainSize()/2;
			List<Point> points = new ArrayList<>();
			points.add(startPoint);
			points.add(endPoint);
			double k = startPoint.distance(endPoint.x, endPoint.y);
			double vx = (endPoint.x - startPoint.x)/k;
			double vy = (endPoint.y - startPoint.y)/k;
			while (k > 0){
				Point addPoint = new Point((int)(startPoint.x + k*vx), (int)(startPoint.y + k*vy));
				if (!points.contains(addPoint))
					points.add(addPoint);

				for (int x = addPoint.x - (int)size; x < addPoint.x + size; ++x){
					for (int y = addPoint.y - (int)size; y < addPoint.y + size; ++y){
						if (!points.contains(new Point(x, y)) && x>=0 && x<displayable.getWidth() && y>=0 &&
								y <displayable.getHeight() && addPoint.distance(new Point(x, y)) <= size)
							points.add(new Point(x, y));
					}
				}
				k--;
			}

			for (Point point : points){
				preview.setRGB(point.x, point.y, rgb);
			}
			paintComponent(getGraphics());
		}
	}

	public void clearPreview(){
		preview = null;
		mouseListener.clear();
		if (displayable != null)
			paintComponent(getGraphics());
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		if (preview == null) {
			if (image != null)
				g2.drawImage(image, 0, 0, null);
		}else
			g2.drawImage(preview, 0, 0, null);

		PanelMediator.getInstance().validateScrollPane();
		g2.dispose();
	}
}
