package ru.shurshavchiki;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Vector;

import ru.shurshavchiki.Panels.DrawingPanel;
import ru.shurshavchiki.Panels.InstrumentPanel;
import ru.shurshavchiki.Panels.OneToolPanel;
import ru.shurshavchiki.Panels.SettingPanel;
import ru.shurshavchiki.businessLogic.colorSpace.util.ColorSpaceRegistry;
import ru.shurshavchiki.businessLogic.services.FileService;
import ru.shurshavchiki.businessLogic.services.ImageChangingService;

import javax.swing.*;

public class PanelMediator {

	private FileService fileService = new FileService();

	private DrawingPanel drawingPanel = new DrawingPanel();

	private InstrumentPanel instrumentPanel = new InstrumentPanel();

	private OneToolPanel oneToolPanel = new OneToolPanel();

	private SettingPanel settingPanel = new SettingPanel();;

	private JScrollPane scrollPane = new JScrollPane(drawingPanel);

	public DrawingPanel getDrawingPanel() {
		return this.drawingPanel;
	}

	public InstrumentPanel getInstrumentPanel() {
		return this.instrumentPanel;
	}

	public OneToolPanel getOneToolPanel() {
		return this.oneToolPanel;
	}

	public SettingPanel getSettingPanel() {
		return this.settingPanel;
	}

	public JScrollPane getScrollPane() {
		return this.scrollPane;
	}

	public void openNewImage(File file) throws IOException {
		drawingPanel.loadImage(fileService.readFile(file));
	}

	public void saveImage(File file) throws IOException {
		fileService.saveFile(drawingPanel.getDisplayable(), file);
    }

	public void saveAsImage(File file) throws IOException {
		fileService.saveFile(drawingPanel.getDisplayable(), file);
    }

	public void closeImage() {
	}

	public void exit() {
	}

	public void changeColorSpace(String newSpace){
		// notify service
		// take displayable
//		drawingPanel.loadImage(newDisplayable);
	}

	public void validateScrollPane(){
		scrollPane.setViewportView(drawingPanel);
	}

	public static PanelMediator getInstance() {
		return MediatorHolder.INSTANCE;
	}

	private static class MediatorHolder {
		private static final PanelMediator INSTANCE = new PanelMediator();
	}
}