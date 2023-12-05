package ru.shurshavchiki.Panels.appPanels;

import lombok.Getter;
import ru.shurshavchiki.Listeners.ActionListeners.InstrumentChoseListener;
import ru.shurshavchiki.PanelMediator;

import java.awt.*;
import java.net.URL;

import javax.swing.*;

public class InstrumentPanel extends JPanel {

	@Getter
	private JButton buttonMouse;

	@Getter
	private JButton buttonLine;

	public InstrumentPanel(){

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		ClassLoader classLoader = getClass().getClassLoader();

		URL cursorResource = classLoader.getResource("cursor.png");
		if (cursorResource != null) {
			Icon cursorIcon = new ImageIcon(cursorResource.getPath());
			buttonMouse = new JButton(cursorIcon);
		}else{
			buttonMouse = new JButton("Cursor");
		}
		buttonMouse.setPreferredSize(new Dimension(64, 64));
		buttonMouse.setContentAreaFilled(false);
		buttonMouse.setBorderPainted(false);
		buttonMouse.setOpaque(false);

		URL lineResource = classLoader.getResource("line.png");
		if (lineResource != null) {
			Icon lineIcon = new ImageIcon(lineResource.getPath());
			buttonLine = new JButton(lineIcon);
		}else{
			buttonLine = new JButton("Line");
		}
		buttonLine.setPreferredSize(new Dimension(64, 64));
		buttonLine.setContentAreaFilled(false);
		buttonLine.setBorderPainted(false);
		buttonLine.setOpaque(false);

		this.add(buttonMouse);
		this.add(buttonLine);
		buttonMouse.addActionListener(new InstrumentChoseListener(PanelMediator.getInstance().getOneToolPanel()));
		buttonLine.addActionListener(new InstrumentChoseListener(PanelMediator.getInstance().getOneToolPanel()));
		buttonMouse.setActionCommand("cursor");
		buttonLine.setActionCommand("line");
		
		ButtonGroup group = new ButtonGroup();
		group.add(buttonMouse);
		group.add(buttonLine);
		buttonMouse.doClick();
	}
}
