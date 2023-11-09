package ru.shurshavchiki.Panels;

import lombok.Getter;
import ru.shurshavchiki.Helpers.ChannelColorHelper;
import ru.shurshavchiki.Listeners.MouseListener;
import ru.shurshavchiki.PanelMediator;
import ru.shurshavchiki.businessLogic.domain.entities.Displayable;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.Histogram;

import java.util.Comparator;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class HistogramPanel extends JPanel {

	private Color color;

	private List<Integer> values;

	public HistogramPanel(Histogram histogram){
		color = ChannelColorHelper.map(histogram.ChannelName());
		values = histogram.ValueDistribution();
		setPreferredSize(new Dimension(256, 200));
		setMinimumSize(new Dimension(256, 200));
		setSize(new Dimension(256, 200));
	}

	@Override
	protected void paintComponent(Graphics gh) {
		Graphics2D his = (Graphics2D)gh;
		his.setColor(color);

		Integer maxValue = values.stream().max(Integer::compare).orElseThrow();

		for (int i = 0; i < 256; ++i){
			his.fillRect(i, 200 - (200 / maxValue) * values.get(i), 1, (200 / maxValue) * values.get(i));
		}
	}
}
