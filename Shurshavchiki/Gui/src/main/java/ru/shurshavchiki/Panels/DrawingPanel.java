package ru.shurshavchiki.Panels;

import ru.shurshavchiki.businessLogic.entities.Displayable;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class DrawingPanel extends JPanel {

	private Displayable pnmFile = null;
	private boolean flag = true;

	public DrawingPanel(){
		setBackground(Color.WHITE);
	}

	public void noteImageChanged() {
		flag = false;
	}

	public void loadImage(Displayable pnmFile) {
		this.setPnmFile(pnmFile);
	}

	public void setPnmFile(Displayable pnmFile) {
		this.pnmFile = pnmFile;
	}

	public Displayable getPnmFile() {
		return this.pnmFile;
	}

	@Override
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


    }


}
