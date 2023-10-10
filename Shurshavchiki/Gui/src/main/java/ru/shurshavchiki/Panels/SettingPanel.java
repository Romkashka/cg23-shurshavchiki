package ru.shurshavchiki.Panels;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.Listeners.ColorChannelListener;
import ru.shurshavchiki.Listeners.ColorSpaceListener;
import ru.shurshavchiki.Listeners.FileButtonListener;
import ru.shurshavchiki.PanelMediator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class SettingPanel extends JPanel{

	@Getter
	private JMenuBar menuBar = new JMenuBar();

	@Getter
	@Setter
	private File selectedFile = null;

	private JMenu menuColorChannels;

	@Getter
	private String chosenColorSpace;

	@Getter
	private String chosenChannels;

	public SettingPanel(){
		configureMenuFile();
		configureMenuEdit();
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

	public void configureMenuEdit(){
		JMenu editMenu = new JMenu("Edit");
		createColorSpaceBox(editMenu);
		createColorChannels(editMenu);

		menuBar.add(editMenu);
	}

	private void createColorSpaceBox(JMenu editMenu){
		List<String> list = PanelMediator.getInstance().getListColorSpaces();
		JMenu menuColorSpace = new JMenu("Color Space");

		ColorSpaceListener.Observer observer = new ColorSpaceListener.Observer(this);

		for (var o : list){
			menuColorSpace.add(new ColorSpaceListener(o, o, observer));
		}

		chosenColorSpace = list.get(0);
		editMenu.add(menuColorSpace);
	}

	private void createColorChannels(JMenu editMenu){
		List<String> list = new ArrayList<>();
		list.add("All");
		list.addAll(PanelMediator.getInstance().getListColorChannels(chosenColorSpace));
		menuColorChannels = new JMenu("Color Channels");

		ColorChannelListener.Observer observer = new ColorChannelListener.Observer(this);

		for (var o : list){
			menuColorChannels.add(new ColorChannelListener(o, o, observer));
		}

		chosenChannels = list.get(0);
		editMenu.add(menuColorChannels);
	}

	public void changeChosenColorSpace(String space){
		PanelMediator.getInstance().changeColorSpace(space);
		chosenColorSpace = space;
		changeChannelList();
		PanelMediator.getInstance().createPreview(chosenColorSpace, chosenChannels);
	}

	public void changeChannelList(){
		List<String> list = new ArrayList<>();
		list.add("All");
		list.addAll(PanelMediator.getInstance().getListColorChannels(chosenColorSpace));
		menuColorChannels.removeAll();

		ColorChannelListener.Observer observer = new ColorChannelListener.Observer(this);

		for (var o : list){
			menuColorChannels.add(new ColorChannelListener(o, o, observer));
		}

		chosenChannels = list.get(0);
	}

	public void changeChosenChannels(String channel){

	}
}
