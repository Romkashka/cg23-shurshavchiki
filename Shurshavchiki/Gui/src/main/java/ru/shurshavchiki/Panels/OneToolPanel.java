package ru.shurshavchiki.Panels;

import lombok.Getter;
import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.PanelMediator;

import javax.swing.*;
import java.awt.*;

public class OneToolPanel extends JPanel {

	@Getter
	private String chosen;

	JTextPane title = new JTextPane();

	JSpinner spinnerFirstChannel;

	JTextPane firstChannel = new JTextPane();

	JSpinner spinnerSecondChannel;

	JTextPane secondChannel = new JTextPane();

	JSpinner spinnerThirdChannel;

	JTextPane thirdChannel = new JTextPane();

	public OneToolPanel(){
		title.setEditable(false);
		title.setVisible(false);
		title.setOpaque(false);
		title.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 24));

		SpinnerModel modelFirst = new SpinnerNumberModel(0, 0, 255, 1);
		firstChannel.setEditable(false);
		firstChannel.setVisible(false);
		firstChannel.setOpaque(false);
		firstChannel.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 16));
		spinnerFirstChannel = new JSpinner(modelFirst);
		spinnerFirstChannel.setVisible(false);
		spinnerFirstChannel.setOpaque(false);

		SpinnerModel modelSecond = new SpinnerNumberModel(0, 0, 255, 1);
		secondChannel.setEditable(false);
		secondChannel.setVisible(false);
		secondChannel.setOpaque(false);
		secondChannel.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 16));
		spinnerSecondChannel = new JSpinner(modelSecond);
		spinnerSecondChannel.setVisible(false);
		spinnerSecondChannel.setOpaque(false);

		SpinnerModel modelThird = new SpinnerNumberModel(0, 0, 255, 1);
		thirdChannel.setEditable(false);
		thirdChannel.setVisible(false);
		thirdChannel.setOpaque(false);
		thirdChannel.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 16));
		spinnerThirdChannel = new JSpinner(modelThird);
		spinnerThirdChannel.setVisible(false);
		spinnerThirdChannel.setOpaque(false);

		this.setLayout(new GridBagLayout());
		GridBagHelper gridSetter = new GridBagHelper();
		gridSetter.fillHorizontally();
		this.add(title);
		gridSetter.insertEmptyRow(this, 10);
		gridSetter.nextRow().fillHorizontally();
		this.add(firstChannel);
		this.add(spinnerFirstChannel);
		gridSetter.insertEmptyRow(this, 10);
		gridSetter.nextRow().fillHorizontally();
		this.add(secondChannel);
		this.add(spinnerSecondChannel);
		gridSetter.insertEmptyRow(this, 10);
		gridSetter.nextRow().fillHorizontally();
		this.add(thirdChannel);
		this.add(spinnerThirdChannel);
		gridSetter.insertEmptyFiller(this);
	}

	public void setupCursor(){
		clearAll();
		title.setText("Cursor");
		title.setVisible(true);
		chosen = "Cursor";
	}

	public void setupLine(){
		clearAll();;
		title.setText("Line");
		var channels = PanelMediator.getInstance().getListColorChannels(PanelMediator.getInstance().getSettingPanel().getChosenColorSpace());
		firstChannel.setText(channels.get(0));
		secondChannel.setText(channels.get(1));
		thirdChannel.setText(channels.get(2));
		title.setVisible(true);
		firstChannel.setVisible(true);
		secondChannel.setVisible(true);
		thirdChannel.setVisible(true);
		spinnerFirstChannel.setVisible(true);
		spinnerSecondChannel.setVisible(true);
		spinnerThirdChannel.setVisible(true);
		chosen = "Line";
	}

	private void clearAll(){
		title.setVisible(false);
		firstChannel.setVisible(false);
		secondChannel.setVisible(false);
		thirdChannel.setVisible(false);
		spinnerFirstChannel.setVisible(false);
		spinnerSecondChannel.setVisible(false);
		spinnerThirdChannel.setVisible(false);
	}
}
