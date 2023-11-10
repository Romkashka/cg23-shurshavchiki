package ru.shurshavchiki;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.Frames.ImageFrame;
import ru.shurshavchiki.Panels.DrawingPanel;
import ru.shurshavchiki.Panels.InstrumentPanel;
import ru.shurshavchiki.Panels.OneToolPanel;
import ru.shurshavchiki.Panels.SettingPanel;
import ru.shurshavchiki.businessLogic.api.*;
import ru.shurshavchiki.businessLogic.colorSpace.util.ColorSpaceRegistry;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.domain.models.RgbPixel;
import ru.shurshavchiki.businessLogic.drawing.models.Line;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.Histogram;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingParameters;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters.ScalingAlgorithmParameter;

import javax.swing.*;

public class PanelMediator {
	@Getter
	private Boolean somethingChanged = false;

	@Getter
	private DrawingPanel drawingPanel = new DrawingPanel();

	private Context mainContext = new ServiceFactoryImpl().getBlankContext();

	private Context ditheredContext = new ServiceFactoryImpl().getBlankContext();

	private Boolean wasDithered = false;

	private final FileProcessingService fileProcessingService = new ServiceFactoryImpl().getFileProcessingService();

	private final ImageProcessingService imageProcessingService = new ServiceFactoryImpl().getImageProcessingService();

	private final ConversionService conversionService = new ServiceFactoryImpl().getConversionService();

	private final DrawingService drawingService = new ServiceFactoryImpl().getDrawingService();

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
	private JScrollPane scrollPane;

	public void run() {
		scrollPane = new JScrollPane(drawingPanel);
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
		scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
		imageFrame.create();
	}

	public void createImage(int width, int height, String type) throws IOException {
		mainContext.setNewImageParameters(height, width);
		mainContext.setImageCreationAlgorithm(type);
		fileProcessingService.createNewImage(mainContext);
		drawingPanel.loadImage(mainContext.getShownDisplayable());
		setGammaDefault();
		settingPanel.enableImageButtons();
		somethingChanged = true;
	}

	public void openNewImage(File file) throws IOException {
		mainContext.setFile(file);
		fileProcessingService.openImage(mainContext);
		drawingPanel.loadImage(mainContext.getShownDisplayable());
		setGammaDefault();
		settingPanel.enableImageButtons();
		settingPanel.setFileTitle(file.getAbsolutePath());
	}

	public void saveImage() throws IOException {
		fileProcessingService.saveImage(mainContext);
		somethingChanged = false;
	}

	public void saveAsImage(File file) throws IOException {
		if (wasDithered){
			fileProcessingService.saveImageAs(ditheredContext, file);
			wasDithered = false;
		}else{
			fileProcessingService.saveImageAs(mainContext, file);
		}
		somethingChanged = false;
	}

	public Boolean getConfirm(){
		int answer = JOptionPane.showConfirmDialog(null,
				"Some changes weren't save. Are you sure you want to close the picture?",
				"Close opened picture", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
        return answer == JOptionPane.YES_OPTION;
	}

	public void closeImage() {
		somethingChanged = false;
		drawingPanel.closeImage();
		setGammaDefault();
		settingPanel.disableImageButtons();
		settingPanel.eraseFileTitle();
		Context newMainContext = new ServiceFactoryImpl().getBlankContext();
		newMainContext.chooseColorSpace(settingPanel.getChosenColorSpace());
		newMainContext.chooseChannel(settingPanel.getChosenChannels());
		mainContext = newMainContext;
	}

	public void exit() {
		imageFrame.dispose();
	}

    public List<String>getListColorSpaces(){
        return mainContext.getColorSpacesNames();
    }

    public List<String> getListColorChannels(String space){
        return new ColorSpaceRegistry().getFactoryByName(space)
                .getColorSpace().Channels().stream().map(Enum::name).toList();
    }

	public String[] getGenerateTypes(){
		return mainContext.getAllImageCreationAlgorithms().toArray(new String[0]);
	}

	public String[] getDitheringAlgorithms(){
		return mainContext.getAllDitheringAlgorithms().toArray(new String[0]);
	}

	public List<String> getScaleAlgorithms(){
		return mainContext.getScalingAlgorithms();
	}

	public ScalingAlgorithm getScaleAlgorithm(String algorithm){
		mainContext.setScalingAlgorithm(algorithm);
		return mainContext.getScalingAlgorithm();
	}

	public void setDisplayableDithered(String selectedAlgorithm, int selectedBitRate){
		mainContext.setDitheringAlgorithm(selectedAlgorithm);
		mainContext.setDitheringAlgorithmBitRate(selectedBitRate);
		ditheredContext = imageProcessingService.ditherImage(mainContext);
		wasDithered = true;
	}

	public Displayable getDitheredPreview(String selectedAlgorithm, int selectedBitRate){
		mainContext.setDitheringAlgorithm(selectedAlgorithm);
		mainContext.setDitheringAlgorithmBitRate(selectedBitRate);
		return imageProcessingService.ditherImage(mainContext).getShownDisplayable();
	}

    public void createPreview(){
		if (drawingPanel.getDisplayable() != null)
			drawingPanel.loadImage(mainContext.getShownDisplayable());
    }

	public void validateScrollPane(){
		scrollPane.getViewport().setView(drawingPanel);
		scrollPane.validate();
	}

	public void changeChannel(List<String> channel) {
		mainContext.chooseChannel(channel);
		if (mainContext.isEmpty()) {
			return;
		}
		conversionService.applyColorFilters(mainContext);
		if (drawingPanel.getDisplayable() != null)
			somethingChanged = true;
	}

	public void changeColorSpace(String newSpace){
		mainContext.chooseColorSpace(newSpace);
		if (mainContext.isEmpty()) {
			return;
		}
		conversionService.applyColorFilters(mainContext);
		if (drawingPanel.getDisplayable() != null)
			somethingChanged = true;
	}

	public void makeAutocorrection(float lower, float upper){
		mainContext.chooseContrastCorrector(lower, upper);
		imageProcessingService.autocorrectImage(mainContext);
		drawingPanel.loadImage(mainContext.getShownDisplayable());
	}

	public void assignGamma(float displayGamma) {
		mainContext.chooseGamma(displayGamma);
		conversionService.assignGamma(mainContext);
	}

	public void convertGamma(float fileGamma) {
		mainContext.chooseGamma(fileGamma);
		conversionService.convertGamma(mainContext);
	}

	private void setGammaDefault(){
		settingPanel.setFileGamma(0F);
		settingPanel.setDisplayGamma(0F);
	}

	public List<Histogram> getHistogramsInfo(){
		imageProcessingService.generateHistograms(mainContext);
		return mainContext.getHistograms();
	}

	public void drawLine(Point start, Point end){
		mainContext.setLineBase("Solid");
		mainContext.setStartLineTip("None");
		mainContext.setEndLineTip("None");
		RgbPixel color = new RgbPixel(((float)oneToolPanel.getMainColor().getRed())/255.f,
				((float)oneToolPanel.getMainColor().getGreen())/255.f,
				((float)oneToolPanel.getMainColor().getBlue())/255.f);
		mainContext.setNewLine( new Line(start, end, oneToolPanel.getMainSize(), color, (float)oneToolPanel.getMainColor().getAlpha()/255.f));
		drawingService.drawLine(mainContext);
		drawingPanel.loadImage(mainContext.getShownDisplayable());
	}

	public void scaleImage(String algorithm, List<ScalingAlgorithmParameter> algorithmParameters, ScalingParameters parameters){
		mainContext.setScalingAlgorithm(algorithm);
		mainContext.initScalingAlgorithm(algorithmParameters);
		mainContext.setScalingParameters(parameters);
		imageProcessingService.scaleImage(mainContext);
		drawingPanel.loadImage(mainContext.getShownDisplayable());
	}

	public static PanelMediator getInstance() {
		return MediatorHolder.INSTANCE;
	}

	private static class MediatorHolder {
		private static final PanelMediator INSTANCE = new PanelMediator();
	}
}