package Gui.Panels;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

import Gui.PanelMediator;

public class SettingPanel extends JPanel {
	
	private JButton openButton, saveButton, saveAsButton;
	
	public SettingPanel(){
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		openButton = new JButton("Open");
		this.add(openButton);
		openButton.addActionListener(new ButtonListener());
		
		saveButton = new JButton("Save");
		this.add(saveButton);
		saveButton.addActionListener(new ButtonListener());
		
		saveAsButton = new JButton("Save As");
		this.add(saveAsButton);
		saveAsButton.addActionListener(new ButtonListener());
	}
	
	private class ButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (openButton.getModel().isArmed()) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(SettingPanel.this);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
					PanelMediator.INSTANCE.openNewImage(selectedFile);
				}
			}else if(saveButton.getModel().isArmed()) {
				PanelMediator.INSTANCE.saveNewImage();
			}else if(saveAsButton.getModel().isArmed()) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(SettingPanel.this);
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
					PanelMediator.INSTANCE.saveAsNewImage(selectedFile);
				}
			}
		}
	}
}
