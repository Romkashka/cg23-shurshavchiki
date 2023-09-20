package GUI.Panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import GUI.Frames.InputFrame;

public class SettingPanel extends JPanel {
	
	private JButton inputButton;
	
	public SettingPanel(){

		inputButton = new JButton("Open");
		this.add(inputButton);
		inputButton.addActionListener(new ButtonListener());
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (inputButton.getModel().isArmed()) {
				var inputFrame = new InputFrame();
			}
		}
	}
}
