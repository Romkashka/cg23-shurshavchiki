package Gui.Panels;

import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import Gui.PanelMediator;

public class SettingPanel extends JPanel{

	private JMenuBar menuBar = new JMenuBar();
	
	public SettingPanel(){
	    JMenu fileMenu = new JMenu("File");
	    JMenu editMenu = new JMenu("Edit");
	    
	    JMenuItem newMenuItem = new JMenuItem("New");
	    newMenuItem.setMnemonic(KeyEvent.VK_N);
	    newMenuItem.setActionCommand("New");

	    JMenuItem openMenuItem = new JMenuItem("Open");
	    openMenuItem.setActionCommand("Open");

	    JMenuItem saveMenuItem = new JMenuItem("Save");
	    saveMenuItem.setActionCommand("Save");
	    
	    JMenuItem saveAsMenuItem = new JMenuItem("Save As");
	    saveAsMenuItem.setActionCommand("SaveAs");
	    
	    JMenuItem closeMenuItem = new JMenuItem("Close");
	    closeMenuItem.setActionCommand("Close");

	    JMenuItem exitMenuItem = new JMenuItem("Exit");
	    exitMenuItem.setActionCommand("Exit"); 
	    
	    newMenuItem.addActionListener(new ButtonListener());
	    openMenuItem.addActionListener(new ButtonListener());
	    saveMenuItem.addActionListener(new ButtonListener());
	    saveAsMenuItem.addActionListener(new ButtonListener());
	    closeMenuItem.addActionListener(new ButtonListener());
	    exitMenuItem.addActionListener(new ButtonListener());
		
	    fileMenu.add(newMenuItem);
	    fileMenu.add(openMenuItem);
	    fileMenu.add(saveMenuItem);
	    fileMenu.add(saveAsMenuItem);
	    fileMenu.add(closeMenuItem);
	    fileMenu.add(exitMenuItem);
	    
	    menuBar.add(fileMenu);
	    menuBar.add(editMenu);
	}
	
	public JMenuBar getMenuBar() {
		return menuBar;
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent event) {
			if (event.getActionCommand().equals("Open")) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showOpenDialog(SettingPanel.this);
				
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
					PanelMediator.INSTANCE.openNewImage(selectedFile);
				}
			}
			
			if(event.getActionCommand().equals("Save")) {
				PanelMediator.INSTANCE.saveNewImage();
			}
			
			if(event.getActionCommand().equals("SaveAs")) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
				int result = fileChooser.showSaveDialog(SettingPanel.this);
				
				if (result == JFileChooser.APPROVE_OPTION) {
				    File selectedFile = fileChooser.getSelectedFile();
					PanelMediator.INSTANCE.saveAsNewImage(selectedFile);
				}
			}

			if(event.getActionCommand().equals("Close")) {
				PanelMediator.INSTANCE.closeImage();
			}
			
			if(event.getActionCommand().equals("Exit")) {
				PanelMediator.INSTANCE.exit();
			}
		}
	}
}
