package ru.shurshavchiki.Panels;

import ru.shurshavchiki.Listeners.ColorChannelListener;
import ru.shurshavchiki.Listeners.ColorSpaceListener;
import ru.shurshavchiki.Listeners.FileButtonListener;

import java.io.File;
import java.util.Vector;

import javax.swing.*;

public class SettingPanel extends JPanel{

	private JMenuBar menuBar = new JMenuBar();
	private File selectedFile = null;

	public SettingPanel(){
		configureMenuFile();
		configureMenuEdit();
	}

	public File getSelectedFile() {
		return this.selectedFile;
	}

	public void setSelectedFile(File selectedFile) {
		this.selectedFile = selectedFile;
	}

	public JMenuBar getMenuBar() {
		return this.menuBar;
	}

	private void configureMenuFile(){
		JMenu fileMenu = new JMenu("File");
		JMenuItem newMenuItem = new JMenuItem("New");
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

		newMenuItem.addActionListener(new FileButtonListener());
		openMenuItem.addActionListener(new FileButtonListener());
		saveMenuItem.addActionListener(new FileButtonListener());
		saveAsMenuItem.addActionListener(new FileButtonListener());
		closeMenuItem.addActionListener(new FileButtonListener());
		exitMenuItem.addActionListener(new FileButtonListener());

		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		fileMenu.add(closeMenuItem);
		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);
	}

	private void configureMenuEdit(){
		JMenu editMenu = new JMenu("Edit");
		createColorSpaceBox(editMenu);
		createColorChannels(editMenu);

		menuBar.add(editMenu);
	}

	private void createColorSpaceBox(JMenu editMenu){
		//Vector<String> list = PanelMediator.getListColorSpaces();
		Vector<String> list = new Vector<>();
		list.add("RGB");
		list.add("NE RGB");
		JMenu menuColorSpace = new JMenu("Color Space");

		ColorSpaceListener.Observer observer = new ColorSpaceListener.Observer();

		for (var o : list){
			menuColorSpace.add(new ColorSpaceListener(o, o, observer));
		}

		editMenu.add(menuColorSpace);
	}

	private void createColorChannels(JMenu editMenu){
		//Vector<String> list = PanelMediator.getListColorSpaces();
		Vector<String> list = new Vector<>();
		list.add("All");
		list.add("Red");
		list.add("Green");
		list.add("Blue");
		JMenu menuColorChannels = new JMenu("Color Channels");

		ColorChannelListener.Observer observer = new ColorChannelListener.Observer();

		for (var o : list){
			menuColorChannels.add(new ColorChannelListener(o, o, observer));
		}

		editMenu.add(menuColorChannels);
	}
}
