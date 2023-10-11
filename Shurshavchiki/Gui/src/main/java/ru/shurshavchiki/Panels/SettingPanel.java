package ru.shurshavchiki.Panels;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.ExceptionHandler;
import ru.shurshavchiki.Listeners.ColorChannelListener;
import ru.shurshavchiki.Listeners.ColorSpaceListener;
import ru.shurshavchiki.Listeners.FileButtonListener;
import ru.shurshavchiki.PanelMediator;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;

public class SettingPanel extends JPanel{

	@Getter
	private JMenuBar menuBar = new JMenuBar();

	@Getter
	@Setter
	private File selectedFile = null;

	private JMenu menuColorSpace;

	private JMenu menuColorChannels;

	@Getter
	private String chosenColorSpace;

	@Getter
	private List<String> chosenChannels;

	@Setter
	private float fileGamma = 0;

	private float displayGamma = 0;

	private final Color selected = new Color(179, 255, 179);

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
		fileMenu.addSeparator();
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
		fileMenu.add(closeMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);
	}

	public void configureMenuEdit(){
		JMenu editMenu = new JMenu("Edit");
		createColorSpaceBox(editMenu);
		createColorChannels(editMenu);
		editMenu.addSeparator();
		createGammaConvert(editMenu);
		createGammaAssign(editMenu);
		menuBar.add(editMenu);
	}

	private void createColorSpaceBox(JMenu editMenu){
		List<String> list = PanelMediator.getInstance().getListColorSpaces();
		menuColorSpace = new JMenu("Color Space");

		for (var o : list){
			menuColorSpace.add(new ColorSpaceListener(o, o, this));
		}

		chosenColorSpace = list.get(0);
		PanelMediator.getInstance().getFileService().chooseColorSpace(chosenColorSpace);
		menuColorSpace.getItem(0).setBackground(selected);
		editMenu.add(menuColorSpace);
	}

	private void createColorChannels(JMenu editMenu){
        List<String> list = new ArrayList<>
				(PanelMediator.getInstance().getListColorChannels(chosenColorSpace));
		menuColorChannels = new JMenu("Color Channels");

		for (var o : list){
			menuColorChannels.add(new ColorChannelListener(o, o, this, selected));
		}

		chosenChannels = list;
		editMenu.add(menuColorChannels);
	}

	public void changeChosenColorSpace(ColorSpaceListener colorSpaceListener){
		for (var menuItem = 0; menuItem < menuColorSpace.getItemCount(); ++menuItem){
			menuColorSpace.getItem(menuItem).setBackground(Color.white);
		}
		PanelMediator.getInstance().changeColorSpace(colorSpaceListener.getColorSpace());
		chosenColorSpace = colorSpaceListener.getColorSpace();
		colorSpaceListener.setBackground(selected);
		changeChannelList();
		PanelMediator.getInstance().createPreview();
	}

	private void changeChannelList(){
        List<String> list = new ArrayList<>
				(PanelMediator.getInstance().getListColorChannels(chosenColorSpace));
		menuColorChannels.removeAll();

		for (var o : list){
			menuColorChannels.add(new ColorChannelListener(o, o, this, selected));
		}

		chosenChannels = list;
	}

	public void changeChosenChannels(ColorChannelListener colorChannelListener){
		PanelMediator.getInstance().changeChannel(colorChannelListener.getColorChannel());
		if (chosenChannels.contains(colorChannelListener.getColorChannel())){
			chosenChannels.remove(colorChannelListener.getColorChannel());
			colorChannelListener.setBackground(Color.white);
		} else {
			chosenChannels.add(colorChannelListener.getColorChannel());
			colorChannelListener.setBackground(selected);
		}

		PanelMediator.getInstance().createPreview();
	}

	public void createGammaConvert(JMenu editMenu){
		editMenu.add(new AbstractAction("Convert Gamma") {
			@Override
			public void actionPerformed(ActionEvent event) {
				String input = JOptionPane.showInputDialog("Enter gamma to convert");
				try {
					fileGamma = Float.parseFloat(input);
					if (fileGamma != 0 && fileGamma < 1)
						throw new NumberFormatException("Gamma can not equals " + fileGamma);
					PanelMediator.getInstance().convertGamma(fileGamma);
					PanelMediator.getInstance().createPreview();
				}catch (Exception exception){
					new ExceptionHandler().handleException(exception);
				}
			}
		});
	}

	public void createGammaAssign(JMenu editMenu){
		editMenu.add(new AbstractAction("Assign Gamma") {
			@Override
			public void actionPerformed(ActionEvent event) {
				String input = JOptionPane.showInputDialog("Enter gamma to assign");
				try {
					displayGamma = Float.parseFloat(input);
					if (displayGamma != 0 && displayGamma < 1)
						throw new NumberFormatException("Gamma can not equals " + displayGamma);
					PanelMediator.getInstance().assignGamma(displayGamma);
					PanelMediator.getInstance().createPreview();
				}catch (Exception exception){
					new ExceptionHandler().handleException(exception);
				}
			}
		});
	}
}
