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

	JTextPane firstAlpha = new JTextPane();

	public OneToolPanel(){
		title.setEditable(false);
		title.setVisible(false);
		title.setOpaque(false);
		title.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 24));

		buttonMainColor = new JButton("Color");
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

		SpinnerModel modelFirst = new SpinnerNumberModel(100, 0, 100, 1);
		firstAlpha.setEditable(false);
		firstAlpha.setVisible(false);
		firstAlpha.setOpaque(false);
		firstAlpha.setFont(new Font(Font.SANS_SERIF,  Font.PLAIN, 12));
		spinnerFirstAlpha = new JSpinner(modelFirst);
		spinnerFirstAlpha.setVisible(false);
		spinnerFirstAlpha.setOpaque(false);

		this.setLayout(new GridBagLayout());
		GridBagHelper gridSetter = new GridBagHelper();
		gridSetter.nextCell().fillHorizontally();
		this.add(title, gridSetter.get());
		gridSetter.insertEmptyRow(this, 10);
		gridSetter.nextRow().nextCell().alignLeft();
		this.add(buttonMainColor, gridSetter.get());
		gridSetter.nextCell();
		this.add(mainColorPreview, gridSetter.get());
		gridSetter.nextRow().nextCell().alignLeft();
		this.add(firstAlpha, gridSetter.get());
		gridSetter.nextCell();
		this.add(spinnerFirstAlpha, gridSetter.get());
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
		title.setVisible(true);
		firstAlpha.setText("Alpha:");
		buttonMainColor.setVisible(true);
		mainColorPreview.setVisible(true);
		firstAlpha.setVisible(true);
		spinnerFirstAlpha.setVisible(true);
		chosen = "Line";
	}

	private void clearAll(){
		title.setVisible(false);
		buttonMainColor.setVisible(false);
		mainColorPreview.setVisible(false);
		firstAlpha.setVisible(false);
		spinnerFirstAlpha.setVisible(false);
	}

	public void setMainColor(Color color){
		mainColor = color;
		mainColorPreview.setBackground(mainColor);
	}
}
