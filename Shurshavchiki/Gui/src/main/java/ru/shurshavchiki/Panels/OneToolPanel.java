package ru.shurshavchiki.Panels;

import lombok.Getter;
import lombok.Setter;
import ru.shurshavchiki.Frames.ColorChooserFrame;
import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.Listeners.InstrumentChoseListener;
import ru.shurshavchiki.PanelMediator;

import javax.swing.*;
import java.awt.*;

public class OneToolPanel extends JPanel {

	@Getter
	private String chosen;

	private JTextPane title = new JTextPane();

	private JButton buttonMainColor;

	private JLabel mainColorPreview = new JLabel();

	@Getter
	private Color mainColor = new Color(0, 0, 0);

	JSpinner spinnerFirstAlpha;

	JTextPane firstAlpha;

	JSpinner spinnerMainSize;

	JTextPane mainSize;

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
		mainColorPreview.setText(" ");
		mainColorPreview.setOpaque(true);
		mainColorPreview.setVisible(false);

		SpinnerModel modelMainColor = new SpinnerNumberModel(100, 0, 100, 1);
		spinnerFirstAlpha = setJSpinner(modelMainColor);
		firstAlpha = setJText();

		SpinnerModel modelWight = new SpinnerNumberModel(16, 0.01, 120, 1);
		spinnerMainSize = setJSpinner(modelWight);
		mainSize = setJText();

		this.setLayout(new GridBagLayout());
		GridBagHelper gridSetter = new GridBagHelper();
		gridSetter.nextCell().fillHorizontally();
		this.add(title, gridSetter.get());
		gridSetter.insertEmptyRow(this, 20);
		gridSetter.nextRow().nextCell().alignCenter();
		this.add(buttonMainColor, gridSetter.get());
		gridSetter.nextCell();
		this.add(mainColorPreview, gridSetter.get());
		gridSetter.insertEmptyRow(this, 10);
		gridSetter.nextRow().nextCell().alignCenter();
		this.add(firstAlpha, gridSetter.get());
		gridSetter.nextCell().alignBottom();
		this.add(spinnerFirstAlpha, gridSetter.get());
		gridSetter.insertEmptyRow(this, 10);
		gridSetter.nextRow().nextCell().alignCenter();
		this.add(mainSize, gridSetter.get());
		gridSetter.nextCell().alignBottom();
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
		firstAlpha.setText("Alpha:");
		mainSize.setText("Thickness:");
		title.setVisible(true);
		buttonMainColor.setVisible(true);
		mainColorPreview.setVisible(true);
		firstAlpha.setVisible(true);
		spinnerFirstAlpha.setVisible(true);
		mainSize.setVisible(true);
		spinnerMainSize.setVisible(true);
		chosen = "Line";
	}

	private void clearAll(){
		title.setVisible(false);
		buttonMainColor.setVisible(false);
		mainColorPreview.setVisible(false);
		firstAlpha.setVisible(false);
		spinnerFirstAlpha.setVisible(false);
		mainSize.setVisible(false);
		spinnerMainSize.setVisible(false);
	}

	public void setMainColor(Color color){
		mainColor = color;
		mainColorPreview.setBackground(mainColor);
	}

	private JSpinner setJSpinner(SpinnerModel spinnerModel){
		JSpinner spinner = new JSpinner(spinnerModel);
		spinner.setPreferredSize(new Dimension(48, 28));
		spinner.setVisible(false);
		spinner.setOpaque(false);
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
