package ru.shurshavchiki;

import java.io.File;
import java.io.IOException;

import ru.shurshavchiki.Panels.DrawingPanel;
import ru.shurshavchiki.Panels.InstrumentPanel;
import ru.shurshavchiki.Panels.OneToolPanel;
import ru.shurshavchiki.Panels.SettingPanel;
import ru.shurshavchiki.businessLogic.services.FileService;

import javax.swing.*;

public class PanelMediator {

	private FileService fileService = new FileService();

	private DrawingPanel drawingPanel = new DrawingPanel();

	private InstrumentPanel instrumentPanel = new InstrumentPanel();

	private OneToolPanel oneToolPanel = new OneToolPanel();

	private SettingPanel settingPanel = new SettingPanel();

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


	//TODO: handle exception properly
	public void openNewImage(File file) throws IOException {
		if (file != null) {
			drawingPanel.loadImage(fileService.readFile(file));
		}
	}

	//TODO: handle exception properly
	public void saveImage(File file) throws IOException {
        if (file != null) {
            fileService.saveFile(drawingPanel.getDisplayable(), file);
        }
    }

	//TODO: handle exception properly
	public void saveAsImage(File file) throws IOException {
        if (file != null) {
            fileService.saveFile(drawingPanel.getDisplayable(), file);
        }
    }

	public void closeImage() {
	}

	public void exit() {
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