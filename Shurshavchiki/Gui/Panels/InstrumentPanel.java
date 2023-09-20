package Gui.Panels;

import java.awt.*;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class InstrumentPanel extends JPanel {

	// private JRadioButton radioBattonPen, radioButtonEraser;
	// private JButton clearButton;
	
	public InstrumentPanel(){

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		/*radioBattonPen = new JRadioButton("Pen", true);
		this.add(radioBattonPen, true);
		radioBattonPen.addActionListener(new ButtonListener());
		
		radioButtonEraser = new JRadioButton("Eraser");
		this.add(radioButtonEraser);
		radioButtonEraser.addActionListener(new ButtonListener());
		
		ButtonGroup group = new ButtonGroup();
		group.add(radioBattonPen);
		group.add(radioButtonEraser);
		
		clearButton = new JButton("Clear");
		this.add(clearButton);
		clearButton.addActionListener(new ButtonListener());*/
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			// if (clearButton.getModel().isArmed()) {
			// }
		}
	}
}
