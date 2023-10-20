package ru.shurshavchiki.Panels;

import lombok.Getter;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class DrawingPanel extends JPanel {
	private BufferedImage image = null;
	@Getter
	private Displayable displayable = null;

	public DrawingPanel(){
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

	public void setDisplayable(Displayable displayable) {
		this.displayable = displayable;
	}

	public void closeImage(){
		displayable = null;
		image = null;
		this.paint(getGraphics());
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, 0, 0, null);
		PanelMediator.getInstance().validateScrollPane();
	}
}
