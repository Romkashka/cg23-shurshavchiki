package ru.shurshavchiki.Panels;

import ru.shurshavchiki.Helpers.ChannelColorHelper;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.Histogram;

import java.util.List;
import javax.swing.*;
import java.awt.*;

public class HistogramPanel extends JPanel {

	private Color color;

	private List<Integer> values;

	private Integer maxValue;

	public HistogramPanel(Histogram histogram, Integer maxValue){
		this.maxValue = maxValue;
		color = ChannelColorHelper.map(histogram.ChannelName());
		values = histogram.ValueDistribution();
		setPreferredSize(new Dimension(300, 200));
		setMinimumSize(new Dimension(300, 200));
		setSize(new Dimension(300, 200));
	}

	@Override
	protected void paintComponent(Graphics gh) {
		Graphics2D his = (Graphics2D)gh;

		his.setColor(new Color(200, 200, 200));
		his.fillRect(44, 0, 300, 200);

		his.setColor(color);
		System.out.println(values.size());

		for (int i = 0; i < 256; ++i){
			his.fillRect(44 + i, 200 - (int)((200. / (float)maxValue) * values.get(i)), 1, (int)((200. / (float)maxValue) * values.get(i)));
		}

		Integer value = values.stream().max(Integer::compare).orElseThrow();
		his.setColor(Color.BLACK);
		his.drawString(value.toString(), 1, 10);
	}
}
