package GUI.Frames;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import GUI.PanelMediator;

public class InputFrame extends JFrame {
	private JButton enterButton;
	private JTextArea text;
	
	public InputFrame() {
		text = new JTextArea();
		
		this.add(text);

		enterButton = new JButton("Enter");
		this.getContentPane().add(enterButton, BorderLayout.EAST);
		enterButton.addActionListener(new ButtonListener());
		
		setTitle("Choose image root");

        this.setSize(400, 100);
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        this.setLocationRelativeTo(null);

        this.setVisible(true);
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (enterButton.getModel().isArmed()) {
				String path = text.getText();
				PanelMediator.INSTANCE.changeImage(path);
				InputFrame.this.dispose();
			}
		}
	}
}
