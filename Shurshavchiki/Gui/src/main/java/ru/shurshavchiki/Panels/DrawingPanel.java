package ru.shurshavchiki.Panels;

import ru.shurshavchiki.businessLogic.entities.Displayable;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class DrawingPanel extends JPanel {

	private BufferedImage image = null;
	private Displayable displayable = null;
	private boolean flag = true;

	public DrawingPanel(){
		setBackground(Color.WHITE);
	}

	public void noteImageChanged() {
		flag = false;
	}

	public void loadImage(Displayable displayable) {
		this.setDisplayable(displayable);
		this.castToBuffer();
		setBackground(Color.WHITE);
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

	public void setDisplayable(Displayable displayable) {
		this.displayable = displayable;
	}

	public Displayable getDisplayable() {
		return this.displayable;
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, 0, 0, null);
	}

  /*@Override
    public void paintComponent(Graphics g) {
    super.paintComponent(g);

    setBackground(Color.WHITE);

    if (pnmFile == null) {
      setSize(800, 600);
    } else if(!flag) {
      flag = true;
      System.out.println("drawing");
      setSize(this.pnmFile.getWidth(), this.pnmFile.getHeight());
      System.out.println("set size");
      for (int x = 0; x < this.pnmFile.getWidth(); x++) {
        for (int y = 0; y < this.pnmFile.getHeight(); y++) {
          var pixel = this.pnmFile.getPixel(x, y);
          g.setColor(new Color(pixel.Red(), pixel.Green(), pixel.Blue()));
          g.fillOval(x, y, 1, 1);
        }
      }
      System.out.println("finish loading");
      repaint();
      System.out.println("repainted");
    }
    }*/


}
