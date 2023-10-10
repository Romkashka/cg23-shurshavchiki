package ru.shurshavchiki;

import java.io.File;
import java.io.IOException;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.Panels.DrawingPanel;
import ru.shurshavchiki.Panels.InstrumentPanel;
import ru.shurshavchiki.Panels.OneToolPanel;
import ru.shurshavchiki.Panels.SettingPanel;
import ru.shurshavchiki.businessLogic.colorSpace.util.ColorSpaceRegistry;
import ru.shurshavchiki.businessLogic.services.FileService;

import javax.swing.*;

public class PanelMediator {

	private FileService fileService = new FileService();

	@Getter
	private DrawingPanel drawingPanel = new DrawingPanel();

	@Getter
	private InstrumentPanel instrumentPanel = new InstrumentPanel();

	@Getter
	private OneToolPanel oneToolPanel = new OneToolPanel();

	@Getter
	@Setter
	private SettingPanel settingPanel;

	@Getter
	private JScrollPane scrollPane = new JScrollPane(drawingPanel);

    private ImageChangingService imageChangingService
            = new ImageChangingService(new ColorSpaceRegistry());

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

    public List<String>getListColorSpaces(){
        return imageChangingService.getColorSpacesNames();
    }

    public List<String> getListColorChannels(String space){
        return new ColorSpaceRegistry().getFactoryByName(space)
                .getColorSpace().Channels().stream().map(Enum::name).toList();
    }

    public void createPreview(String space, String channels){
//		imageChangingService.setColorSpaceConverter();
//
//        imageChangingService.setChannelChooser();
//        var image = imageChangingService.getPreview(drawingPanel.getDisplayable());
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