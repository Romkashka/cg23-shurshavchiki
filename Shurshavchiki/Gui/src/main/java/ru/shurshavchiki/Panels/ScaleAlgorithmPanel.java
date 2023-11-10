package ru.shurshavchiki.Panels;

import ru.shurshavchiki.Helpers.ChannelColorHelper;
import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.Helpers.InputSetHelper;
import ru.shurshavchiki.businessLogic.imageProcessing.autocorrection.Histogram;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.security.AlgorithmParameters;
import java.util.Arrays;
import java.util.List;

public class ScaleAlgorithmPanel extends JPanel {

	public ScaleAlgorithmPanel(){
		setPreferredSize(new Dimension(300, 200));
		setMinimumSize(new Dimension(300, 200));
		setSize(new Dimension(300, 200));
	}

	public void createPanel(ScalingAlgorithm algorithm){
		this.removeAll();
		this.setLayout(new GridBagLayout());
		GridBagHelper gridSetter = new GridBagHelper();

		var parameters = algorithm.getParametersToInit();
		for (var parameter : parameters){
			gridSetter.nextRow().alignLeft();
			JTextPane parameterText = InputSetHelper.setJText();
			parameterText.setText(parameter.getName());
			parameterText.setVisible(true);
			this.add(parameterText, gridSetter.get());
			gridSetter.nextCell().alignCenter();

		}
	}
}
