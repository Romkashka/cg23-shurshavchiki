package ru.shurshavchiki.Panels;

import ru.shurshavchiki.Listeners.ColorChannelListener;
import ru.shurshavchiki.Listeners.ColorSpaceListener;
import ru.shurshavchiki.Listeners.FileButtonListener;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.businessLogic.colorSpace.util.ColorSpaceRegistry;
import ru.shurshavchiki.businessLogic.services.ImageChangingService;

import java.io.File;
import java.util.List;
import java.util.Vector;

import javax.swing.*;

public class SettingPanel extends JPanel{

	private JMenuBar menuBar = new JMenuBar();
	private File selectedFile = null;

	private JMenu menuColorChannels;

	private ImageChangingService imageChangingService
			= new ImageChangingService(new ColorSpaceRegistry());

	private String chosenColorSpace;

	private String chosenChannels;

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

	public void configureMenuEdit(){
		JMenu editMenu = new JMenu("Edit");
		createColorSpaceBox(editMenu);
		createColorChannels(editMenu);

		menuBar.add(editMenu);
	}

	private void createColorSpaceBox(JMenu editMenu){
		List<String> list = getListColorSpaces();
		JMenu menuColorSpace = new JMenu("Color Space");

		ColorSpaceListener.Observer observer = new ColorSpaceListener.Observer(this);

		for (var o : list){
			menuColorSpace.add(new ColorSpaceListener(o, o, observer));
		}

		chosenColorSpace = list.get(0);
		editMenu.add(menuColorSpace);
	}

	private void createColorChannels(JMenu editMenu){
		List<String> list = getListColorChannels(chosenColorSpace);
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
		changeChannelList(space);
	}

	public void changeChannelList(String space){
		List<String> list = getListColorChannels(chosenColorSpace);
		menuColorChannels.removeAll();

		ColorChannelListener.Observer observer = new ColorChannelListener.Observer(this);

		for (var o : list){
			menuColorChannels.add(new ColorChannelListener(o, o, observer));
		}

		chosenChannels = list.get(0);
	}

	public void changeChosenChannels(String channel){

	}

	public String getChosenColorSpace() {
		return chosenColorSpace;
	}

	public String getChosenChannels() {
		return chosenChannels;
	}

	public List<String>getListColorSpaces(){
		return imageChangingService.getColorSpacesNames();
	}

	public static List<String> getListColorChannels(String space){
		return new ColorSpaceRegistry().getFactoryByName(space)
				.getColorSpace().Channels().stream().map(Enum::name).toList();
	}
}
