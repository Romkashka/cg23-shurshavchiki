package ru.shurshavchiki.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

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
		@Override
		public void actionPerformed(ActionEvent event) {
			// if (clearButton.getModel().isArmed()) {
			// }
		}
	}
}
