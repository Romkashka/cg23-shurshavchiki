package ru.shurshavchiki.Panels;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.Frames.ColorChooserFrame;
import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.Listeners.InstrumentChoseListener;
import ru.shurshavchiki.Listeners.SpinnerChangeListener;
import ru.shurshavchiki.PanelMediator;

import javax.swing.*;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class OneToolPanel extends JPanel {

	@Getter
	private String chosen;

	private JTextPane title = new JTextPane();

	private JButton buttonMainColor;

	private JTextArea mainColorPreview = new JTextArea();

	@Getter
	private Color mainColor = new Color(0, 0, 0);

	@Getter
	@Setter
	private Float mainSize = 4f;

	JSpinner spinnerMainSize;

	JTextPane mainSizeText;

	public OneToolPanel(){
		title = setJText();
		title.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 24));

		buttonMainColor = new JButton("Color");
		buttonMainColor.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 18));
		buttonMainColor.setPreferredSize(new Dimension(96, 48));
		buttonMainColor.setContentAreaFilled(true);
		buttonMainColor.setOpaque(false);
		buttonMainColor.setVisible(false);
		buttonMainColor.setActionCommand("main color");
		buttonMainColor.addActionListener(new InstrumentChoseListener(this));

		mainColorPreview.setBackground(mainColor);
		mainColorPreview.setPreferredSize(new Dimension(48, 48));
		mainColorPreview.setOpaque(true);
		mainColorPreview.setEditable(false);
		mainColorPreview.setVisible(false);

		SpinnerModel modelWight = new SpinnerNumberModel(4f, 0.01f, 120f, 1f);
		spinnerMainSize = setJSpinner(modelWight, "main size");
		mainSizeText = setJText();

		this.setLayout(new GridBagLayout());
		GridBagHelper gridSetter = new GridBagHelper();
		gridSetter.nextCell().fillHorizontally();
		this.add(title, gridSetter.get());
		gridSetter.insertEmptyRow(this, 20);
		gridSetter.nextRow().nextCell().alignCenter();
		this.add(buttonMainColor, gridSetter.get());
		gridSetter.nextCell().span();
		this.add(mainColorPreview, gridSetter.get());
		gridSetter.insertEmptyRow(this, 10);
		gridSetter.nextRow().nextCell().alignCenter();
		this.add(mainSizeText, gridSetter.get());
		gridSetter.nextCell();
		this.add(spinnerMainSize, gridSetter.get());
		gridSetter.insertEmptyFiller(this);
	}

	public void setupCursor(){
		clearAll();
		title.setText("Cursor");
		title.setVisible(true);
		chosen = "Cursor";
	}

	public void setupLine(){
		clearAll();
		title.setText("Line");
		mainSizeText.setText("Thickness:");
		title.setVisible(true);
		buttonMainColor.setVisible(true);
		mainColorPreview.setVisible(true);
		mainSizeText.setVisible(true);
		spinnerMainSize.setVisible(true);
		chosen = "Line";
	}

	private void clearAll(){
		title.setVisible(false);
		buttonMainColor.setVisible(false);
		mainColorPreview.setVisible(false);
		mainSizeText.setVisible(false);
		spinnerMainSize.setVisible(false);
		PanelMediator.getInstance().getDrawingPanel().clearPreview();
	}

	public void setMainColor(Color color){
		mainColor = color;
		mainColorPreview.removeAll();
		mainColorPreview.setBackground(mainColor);
	}

	private JSpinner setJSpinner(SpinnerModel spinnerModel, String type){
		JSpinner spinner = new JSpinner(spinnerModel);
		spinner.setPreferredSize(new Dimension(48, 28));
		spinner.setVisible(false);
		spinner.setOpaque(false);
		spinner.addChangeListener(new SpinnerChangeListener(this, type));
		return spinner;
	}

	private JTextPane setJText(){
		JTextPane text = new JTextPane();
		text.setEditable(false);
		text.setVisible(false);
		text.setOpaque(false);
		text.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 18));
		return text;
	}
}
