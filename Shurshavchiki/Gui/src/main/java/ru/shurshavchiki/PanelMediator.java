package ru.shurshavchiki;

import java.io.File;
import java.io.IOException;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.Frames.ImageFrame;
import ru.shurshavchiki.Panels.DrawingPanel;
import ru.shurshavchiki.Panels.InstrumentPanel;
import ru.shurshavchiki.Panels.OneToolPanel;
import ru.shurshavchiki.Panels.SettingPanel;
import ru.shurshavchiki.businessLogic.colorSpace.util.ColorSpaceRegistry;
import ru.shurshavchiki.businessLogic.domain.deprecatedServices.DispatcherService;
import ru.shurshavchiki.businessLogic.domain.deprecatedServices.DispatcherServiceImpl;

import javax.swing.*;

public class PanelMediator {
	@Getter
	private Boolean somethingChanged = false;

	@Getter
	DispatcherService service = DispatcherServiceImpl.getInstance();

	@Getter
	private DrawingPanel drawingPanel = new DrawingPanel();

	@Getter
	@Setter
	private InstrumentPanel instrumentPanel;

	@Getter
	private OneToolPanel oneToolPanel = new OneToolPanel();

	@Getter
	@Setter
	private SettingPanel settingPanel;

	@Getter
	private ImageFrame imageFrame = new ImageFrame();

	@Getter
	private JScrollPane scrollPane = new JScrollPane();

	public void run(){
		imageFrame.create();
		scrollPane.getViewport().add(drawingPanel);
	}

	public void openNewImage(File file) throws IOException {
		service.readFile(file);
		drawingPanel.loadImage(service.getUserProjectDataHolder().getShownDisplayable());
		setGammaDefault();
		settingPanel.enableGammaButtons();
		settingPanel.setFileTitle(file.getAbsolutePath());
	}

	public void saveImage(File file) throws IOException {
		service.saveCurrentFile(file);
    	somethingChanged = false;
	}

	public void saveAsImage(File file) throws IOException {
		service.saveCurrentFile(file);
    	somethingChanged = false;
	}

	public void closeImage() {
		if (somethingChanged){
			int answer = JOptionPane.showConfirmDialog(null,
					"Some changes weren't save. Are you sure you want to close the picture?",
					"close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (answer == JOptionPane.YES_OPTION){
				drawingPanel.closeImage();
				setGammaDefault();
				settingPanel.disableGammaButtons();
				somethingChanged = false;
				settingPanel.eraseFileTitle();
			}
		}else {
			drawingPanel.closeImage();
			setGammaDefault();
			settingPanel.disableGammaButtons();
			settingPanel.eraseFileTitle();
		}
	}

	public void exit() {
		if (somethingChanged){
			int answer = JOptionPane.showConfirmDialog(null,
					"Some changes weren't save. Are you sure you want to close the window?",
					"close", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (answer == JOptionPane.YES_OPTION){
				imageFrame.dispose();
			}
		}else {
			imageFrame.dispose();
		}
	}

    public List<String>getListColorSpaces(){
        return service.getColorSpacesNames();
    }

    public List<String> getListColorChannels(String space){
        return new ColorSpaceRegistry().getFactoryByName(space)
                .getColorSpace().Channels().stream().map(Enum::name).toList();
    }

    public void createPreview(){
		if (drawingPanel.getDisplayable() != null)
			drawingPanel.loadImage(service.getUserProjectDataHolder().getShownDisplayable());
    }

	public void createGammaPreview(){
		if (drawingPanel.getDisplayable() != null)
			drawingPanel.loadImage(service.getUserProjectDataHolder().getShownDisplayable());
	}

	public void validateScrollPane(){
		scrollPane.getViewport().setView(drawingPanel);
	}

	public void changeChannel(List<String> channel) {
		service.chooseChannel(channel);
		if (drawingPanel.getDisplayable() != null)
			somethingChanged = true;
	}

	public void changeColorSpace(String newSpace){
		service.chooseColorSpace(newSpace);
		if (drawingPanel.getDisplayable() != null)
			somethingChanged = true;
	}

	public void assignGamma(float displayGamma) {
		service.assignGamma(displayGamma);
	}

	public void convertGamma(float fileGamma) {
		service.convertGamma(fileGamma);
	}

	private void setGammaDefault(){
		settingPanel.setFileGamma(0F);
		settingPanel.setDisplayGamma(0F);
	}

	public static PanelMediator getInstance() {
		return MediatorHolder.INSTANCE;
	}

	private static class MediatorHolder {
		private static final PanelMediator INSTANCE = new PanelMediator();
	}
}