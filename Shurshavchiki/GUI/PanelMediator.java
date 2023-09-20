package GUI;

import GUI.Panels.DrawingPanel;
import GUI.Panels.InstrumentPanel;
import GUI.Panels.OneToolPanel;
import GUI.Panels.SettingPanel;

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
	
	public void changeImage(String path) {
		drawingPanel.loadImage(path);
		//"C:/Users/1/Desktop/cg23-shurshavchiki/Shurshavchiki/GUI/Resources/icon.png"
	}
}