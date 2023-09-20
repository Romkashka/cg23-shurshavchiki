package Gui;

import java.io.File;

import Gui.Panels.DrawingPanel;
import Gui.Panels.InstrumentPanel;
import Gui.Panels.OneToolPanel;
import Gui.Panels.SettingPanel;

public class PanelMediator {
	private DrawingPanel drawingPanel = new DrawingPanel();
	private InstrumentPanel instrumentPanel = new InstrumentPanel();
	private OneToolPanel oneToolPanel = new OneToolPanel();
	private SettingPanel settingPanel = new SettingPanel();
	
	
	public static final PanelMediator INSTANCE = new PanelMediator();
	
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
	
	public void openNewImage(File file) {
	}
	
	public void saveNewImage() {
	}
	
	public void saveAsNewImage(File file) {
	}
}