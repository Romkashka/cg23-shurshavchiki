package ru.shurshavchiki.Panels;

import lombok.Getter;
import ru.shurshavchiki.Listeners.MouseListener;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class HistogramPanel extends JPanel {

	private BufferedImage image = null;

	@Getter
	private Displayable displayable = null;


	public HistogramPanel(){
		setPreferredSize(new Dimension(256*2, 200));
	}

	@Override
	protected void paintComponent(Graphics gh) {
		Graphics2D his = (Graphics2D)gh;

		List<Integer> example = new ArrayList<>();
		for (int i = 0; i < 256; ++i){
			example.add((i*23)%200);
		}

		for (int i = 0; i < 256; ++i){
			his.fillRect(2*i, 200-example.get(i), 2, example.get(i));
		}
	}
}
