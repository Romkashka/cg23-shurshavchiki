package ru.shurshavchiki;

import java.io.File;
import java.io.IOException;

import ru.shurshavchiki.Panels.DrawingPanel;
import ru.shurshavchiki.Panels.InstrumentPanel;
import ru.shurshavchiki.Panels.OneToolPanel;
import ru.shurshavchiki.Panels.SettingPanel;
import services.FileService;

public class PanelMediator {

	private FileService fileService = new FileService();
	private DrawingPanel drawingPanel = new DrawingPanel();
	private InstrumentPanel instrumentPanel = new InstrumentPanel();
	private OneToolPanel oneToolPanel = new OneToolPanel();
	private SettingPanel settingPanel = new SettingPanel();

//	public static final PanelMediator INSTANCE = new PanelMediator();

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


	//TODO: handle exception properly
	public void openNewImage(File file) throws IOException {
		if (file == null) {

		} else {
			System.out.println("start reading");
			drawingPanel.noteImageChanged();
			drawingPanel.loadImage(fileService.readFile(file));
			System.out.println("read");
		}
	}

	//TODO: handle exception properly
	public void saveImage(File file) throws IOException {
		if (file == null) {

		} else {
			fileService.saveFile(drawingPanel.getPnmFile(), file);
		}
	}

	//TODO: handle exception properly
	public void saveAsImage(File file) throws IOException {
		if (file == null) {

		}else {
			fileService.saveFile(drawingPanel.getPnmFile(), file);
		}
	}

	public void closeImage() {
	}

	public void exit() {
	}

	public static PanelMediator getInstance() {
		return MediatorHolder.INSTANCE;
	}

	private class MediatorHolder {
		private static final PanelMediator INSTANCE = new PanelMediator();
	}
}