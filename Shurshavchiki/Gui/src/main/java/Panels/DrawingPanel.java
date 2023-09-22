package Gui.Panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class DrawingPanel extends JPanel {
	
	private PnmFile pnmFile = null;
	
	public DrawingPanel(){
		setBackground(Color.WHITE);
		drawingList = new ArrayList<Point>();
	}
	
	public void loadImage(PnmFile pnmFile) {
		this.setPnmFile(pnmFile);
		this.drawImage();
	}
	
	public void setPnmFile(PnmFile pnmFile) {
		this.pnmFile = pnmFile;
	}
	
	public PnmFile getPnmFile() {
		return this.pnmFile;
	}
	
	@Override
    public void paintComponent(Graphics g){
        setSize(this.pnmFile.getWidth(), this.pnmFile.getHeight());
        setBackground(Color.WHITE);
        
        for (int x = 0; x < this.pnmFile.getWidth(); x++) {
        	for (int y = 0; y < this.pnmFile.getHeight(); y++) {
        		var pixel = this.pnmFile.getPixel(x, y);
            	g.setColor(new Color(pixel.Red(), pixel.Green(), pixel.Blue()));
                g.fillOval(x, y, 1, 1);
            }
        }

        repaint();
    }
}
