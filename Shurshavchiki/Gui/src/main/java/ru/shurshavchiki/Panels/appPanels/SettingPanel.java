package ru.shurshavchiki.Panels.appPanels;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.Frames.FilterFrame;
import ru.shurshavchiki.Frames.GammaInputFrame;
import ru.shurshavchiki.Frames.HistogramFrame;
import ru.shurshavchiki.Frames.ScaleFrame;
import ru.shurshavchiki.Listeners.ColorListeners.ColorChannelListener;
import ru.shurshavchiki.Listeners.ColorListeners.ColorSpaceListener;
import ru.shurshavchiki.Listeners.ActionListeners.FileButtonListener;
import ru.shurshavchiki.PanelMediator;

import java.awt.*;
import java.awt.event.ActionEvent;
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

	private JMenu menuColorSpace;

	private JMenu menuColorChannels;

	@Getter
	private String chosenColorSpace;

	@Getter
	private List<String> chosenChannels;

	private AbstractAction gammaConvertButton;

	private AbstractAction gammaAssignButton;

	private AbstractAction handleHistogramButton;

	private AbstractAction handleScaleButton;

	private AbstractAction handleFilterButton;

	private JTextPane fileTitle;

	@Setter
	private float fileGamma = 0;

	@Setter
	private float displayGamma = 0;

	private final Color selected = new Color(179, 255, 179);

	JMenuItem newMenuItem = new JMenuItem("New");

	JMenuItem openMenuItem = new JMenuItem("Open");

	JMenuItem saveMenuItem = new JMenuItem("Save");

	JMenuItem saveAsMenuItem = new JMenuItem("Save As");

	JMenuItem exportMenuItem = new JMenuItem("Export");

	JMenuItem ditherMenuItem = new JMenuItem("Dither");

	JMenuItem closeMenuItem = new JMenuItem("Close");

	public SettingPanel(){
		configureMenuFile();
		configureMenuEdit();
		fileTitle = new JTextPane();
		fileTitle.setEnabled(false);
		menuBar.add(fileTitle);
	}

	private void configureMenuFile(){
		JMenu fileMenu = new JMenu("File");

		newMenuItem.setActionCommand("New");
		openMenuItem.setActionCommand("Open");
		saveMenuItem.setActionCommand("Save");
		saveAsMenuItem.setActionCommand("SaveAs");
		exportMenuItem.setActionCommand("Export");
		ditherMenuItem.setActionCommand("Dither");
		closeMenuItem.setActionCommand("Close");
		saveMenuItem.setEnabled(false);
		saveAsMenuItem.setEnabled(false);
		exportMenuItem.setEnabled(false);
		ditherMenuItem.setEnabled(false);
		closeMenuItem.setEnabled(false);

		JMenuItem exitMenuItem = new JMenuItem("Exit");
		exitMenuItem.setActionCommand("Exit");

		newMenuItem.addActionListener(new FileButtonListener());
		openMenuItem.addActionListener(new FileButtonListener());
		saveMenuItem.addActionListener(new FileButtonListener());
		saveAsMenuItem.addActionListener(new FileButtonListener());
		exportMenuItem.addActionListener(new FileButtonListener());
		ditherMenuItem.addActionListener(new FileButtonListener());
		closeMenuItem.addActionListener(new FileButtonListener());
		exitMenuItem.addActionListener(new FileButtonListener());

		fileMenu.add(newMenuItem);
		fileMenu.add(openMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(saveMenuItem);
		fileMenu.add(saveAsMenuItem);
//		fileMenu.add(exportMenuItem);
		fileMenu.add(closeMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(ditherMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitMenuItem);

		menuBar.add(fileMenu);
	}

	public void configureMenuEdit(){
		JMenu editMenu = new JMenu("Edit");
		createColorSpace(editMenu);
		createColorChannels(editMenu);
		editMenu.addSeparator();
		createGammaConvert(editMenu);
		createGammaAssign(editMenu);
		editMenu.addSeparator();
		createHistogram(editMenu);
		createScale(editMenu);
		createFilter(editMenu);

		disableImageButtons();
		menuBar.add(editMenu);
	}

	private void createFilter(JMenu editMenu){
		handleFilterButton = new AbstractAction("Filter") {
			@Override
			public void actionPerformed(ActionEvent event) {
				new FilterFrame();
			}
		};

		editMenu.add(handleFilterButton);
	}

	private void createScale(JMenu editMenu){
		handleScaleButton = new AbstractAction("Scale") {
			@Override
			public void actionPerformed(ActionEvent event) {
				new ScaleFrame();
			}
		};

		editMenu.add(handleScaleButton);
	}

	private void createHistogram(JMenu editMenu){
		handleHistogramButton = new AbstractAction("Autocorrection") {
			@Override
			public void actionPerformed(ActionEvent event) {
				new HistogramFrame();
			}
		};

		editMenu.add(handleHistogramButton);
	}

	private void createColorSpace(JMenu editMenu){
		List<String> list = PanelMediator.getInstance().getListColorSpaces();
		menuColorSpace = new JMenu("Color Space");

		for (var o : list){
			menuColorSpace.add(new ColorSpaceListener(o, o, this));
		}

		chosenColorSpace = list.get(0);
		PanelMediator.getInstance().changeColorSpace(chosenColorSpace);
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
		PanelMediator.getInstance().changeChannel(chosenChannels);
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
		if (chosenChannels.contains(colorChannelListener.getColorChannel())){
			chosenChannels.remove(colorChannelListener.getColorChannel());
			colorChannelListener.setBackground(Color.white);
		} else {
			chosenChannels.add(colorChannelListener.getColorChannel());
			colorChannelListener.setBackground(selected);
		}
		PanelMediator.getInstance().changeChannel(chosenChannels);

		PanelMediator.getInstance().createPreview();
	}

	public void createGammaConvert(JMenu editMenu){
		gammaConvertButton = new AbstractAction("Convert Gamma") {
			@Override
			public void actionPerformed(ActionEvent event) {
                new GammaInputFrame("convert", fileGamma);
			}
		};

		editMenu.add(gammaConvertButton);
	}

	public void createGammaAssign(JMenu editMenu){
		gammaAssignButton = new AbstractAction("Assign Gamma") {
			@Override
			public void actionPerformed(ActionEvent event) {
                new GammaInputFrame("assign", displayGamma);
			}
		};

		editMenu.add(gammaAssignButton);
	}

    public void handleInputGammaAssign(String input){
		displayGamma = Float.parseFloat(input);
		PanelMediator.getInstance().assignGamma(displayGamma);
		PanelMediator.getInstance().createPreview();
    }

    public void handleInputGammaConvert(String input){
		fileGamma = Float.parseFloat(input);
		PanelMediator.getInstance().convertGamma(fileGamma);
		PanelMediator.getInstance().createPreview();
    }

	public void disableImageButtons(){
		gammaConvertButton.setEnabled(false);
		gammaAssignButton.setEnabled(false);
		handleHistogramButton.setEnabled(false);
		handleScaleButton.setEnabled(false);
		handleFilterButton.setEnabled(false);
	}

	public void enableImageButtons(){
		gammaConvertButton.setEnabled(true);
		gammaAssignButton.setEnabled(true);
		handleHistogramButton.setEnabled(true);
		handleScaleButton.setEnabled(true);
		handleFilterButton.setEnabled(true);
	}

	public void setFileTitle(String title){
		fileTitle.setText(title);
		fileTitle.setVisible(true);
	}

	public void eraseFileTitle(){
		fileTitle.setVisible(false);
	}

	public void changeEnable(String str, Boolean bool){
		switch (str){
			case "new" -> newMenuItem.setEnabled(bool);
			case "open" -> openMenuItem.setEnabled(bool);
			case "save" -> saveMenuItem.setEnabled(bool);
			case "saveAs" -> saveAsMenuItem.setEnabled(bool);
			case "export" -> exportMenuItem.setEnabled(bool);
			case "dither" -> ditherMenuItem.setEnabled(bool);
			case "close" -> closeMenuItem.setEnabled(bool);
		}
	}
}
