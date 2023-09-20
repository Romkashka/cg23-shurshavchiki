package GUI.Panels;

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
	
	private ArrayList <Point> drawingList;
	private ImageIcon image;
	
	public DrawingPanel(){
		setBackground(Color.WHITE);
		drawingList = new ArrayList<Point>();
        //image = new ImageIcon("C:/Users/1/Desktop/cg23-shurshavchiki/Shurshavchiki/GUI/Resources/icon.png");
        this.add(new JLabel(image));
	}
	
	public void loadImage(String path) {
		//System.out.print(path);
        image = new ImageIcon(path);
        repaint();
	}
	
	@Override
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    if (image != null) {
	    	g.drawImage (image.getImage(), 0, 0, getWidth (), getHeight (), this);
	    }
	}
}
