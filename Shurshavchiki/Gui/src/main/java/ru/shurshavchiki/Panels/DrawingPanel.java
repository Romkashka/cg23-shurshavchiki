package ru.shurshavchiki.Panels;

import lombok.Getter;
import ru.shurshavchiki.Listeners.MouseListener;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class DrawingPanel extends JPanel {
	private BufferedImage image = null;

	@Getter
	private Displayable displayable = null;

	public DrawingPanel(){
		this.addMouseListener(new MouseListener());
		this.addMouseMotionListener(new MouseListener());
	}

	public void loadImage(Displayable displayable) {
		this.setDisplayable(displayable);
		this.castToBuffer();
		setBackground(Color.WHITE);
		this.paint(getGraphics());
		PanelMediator.getInstance().validateScrollPane();
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

	private void setDisplayable(Displayable displayable) {
		this.displayable = displayable;
	}

	public void closeImage(){
		displayable = null;
		image = null;
		this.paint(getGraphics());
		PanelMediator.getInstance().validateScrollPane();
	}

	public void showLinePreview(Point startPoint, Point endPoint){
		if (displayable != null){
			int rgb = PanelMediator.getInstance().getOneToolPanel().getMainColor().getRGB();
			float size = PanelMediator.getInstance().getOneToolPanel().getMainSize();
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
				image.setRGB(point.x, point.y, rgb);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, 0, 0, null);
	}
}
