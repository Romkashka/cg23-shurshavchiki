package ru.shurshavchiki.Panels;

import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.Helpers.InputSetHelper;
import ru.shurshavchiki.Listeners.ChangeListeners.ScaleListener;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingAlgorithm;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.algorithmParameters.FloatScalingAlgorithmParameter;

import javax.swing.*;
import java.awt.*;

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
			gridSetter.nextRow().alignCenter();
			JTextPane parameterText = InputSetHelper.setJText();
			parameterText.setText(parameter.getName());
			parameterText.setVisible(true);
			this.add(parameterText, gridSetter.get());

			if (parameter instanceof FloatScalingAlgorithmParameter floatParameter){
				SpinnerModel model = new SpinnerNumberModel(floatParameter.getValue(), floatParameter.getLowerLimit(), floatParameter.getUpperLimit(), (floatParameter.getUpperLimit() - floatParameter.getLowerLimit()) / 50);
				JSpinner spinnerModel = new JSpinner(model);
				spinnerModel.setPreferredSize(new Dimension(48, 28));
				spinnerModel.addChangeListener(new ScaleListener(this, parameter.getName(), "float"));
				spinnerModel.setOpaque(false);
				spinnerModel.setVisible(true);
				gridSetter.nextCell().alignCenter();
				this.add(spinnerModel, gridSetter.get());
			}

//			else if (parameter instanceof )
//				spinnerModel.addChangeListener(new ScaleListener(this, parameter.getName(), "int"));
		}
	}

	public void setFloatParameterChange(String name, float value) {

	}

	public void setIntParameterChange(String name, float value) {

	}
}
