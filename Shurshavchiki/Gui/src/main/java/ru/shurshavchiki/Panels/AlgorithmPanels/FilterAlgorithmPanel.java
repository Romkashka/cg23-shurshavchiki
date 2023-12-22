package ru.shurshavchiki.Panels.AlgorithmPanels;

import ru.shurshavchiki.Helpers.GridBagHelper;
import ru.shurshavchiki.Helpers.InputSetHelper;
import ru.shurshavchiki.businessLogic.common.AlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.FloatAlgorithmParameter;
import ru.shurshavchiki.businessLogic.common.IntegerAlgorithmParameter;
import ru.shurshavchiki.businessLogic.imageProcessing.filters.ImageFilter;
import ru.shurshavchiki.businessLogic.imageProcessing.scaling.ScalingAlgorithm;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FilterAlgorithmPanel extends JPanel {

	private List<AlgorithmParameter> algorithmParameters;

	private List<JSpinner> spinners;

	public FilterAlgorithmPanel(){
		setPreferredSize(new Dimension(300, 200));
		setMinimumSize(new Dimension(300, 200));
		setSize(new Dimension(300, 200));
	}

	public List<AlgorithmParameter> getParameterValues(){
		for (int i = 0; i < algorithmParameters.size(); ++i){
			if (algorithmParameters.get(i) instanceof FloatAlgorithmParameter parameter){
				parameter.setValue(((Double)spinners.get(i).getValue()).floatValue());
			}
			if (algorithmParameters.get(i) instanceof IntegerAlgorithmParameter parameter){
				parameter.setValue(((Integer)spinners.get(i).getValue()));
			}
		}
		return algorithmParameters;
	}

	public void createPanel(ImageFilter algorithm){
		algorithmParameters = new ArrayList<>();
		spinners = new ArrayList<>();
		this.removeAll();
		this.setLayout(new GridBagLayout());
		GridBagHelper gridSetter = new GridBagHelper();

		var parameters = algorithm.getAlgorithmParameters();
		for (var parameter : parameters){
			gridSetter.nextRow().nextCell().alignCenter();
			JTextPane parameterText = InputSetHelper.setJText();
			parameterText.setText(parameter.getName());
			parameterText.setVisible(true);
			this.add(parameterText, gridSetter.get());

			if (parameter instanceof FloatAlgorithmParameter floatParameter){
				SpinnerModel model = new SpinnerNumberModel(floatParameter.getValue(), floatParameter.getLowerLimit(), floatParameter.getUpperLimit(), (floatParameter.getUpperLimit() - floatParameter.getLowerLimit()) / 50.f);
				JSpinner spinnerModel = new JSpinner(model);
				spinnerModel.setPreferredSize(new Dimension(48, 28));
				spinnerModel.setOpaque(false);
				spinnerModel.setVisible(true);
				gridSetter.nextCell().alignCenter();
				this.add(spinnerModel, gridSetter.get());
				spinners.add(spinnerModel);
			}

			if (parameter instanceof IntegerAlgorithmParameter intParameter){
				SpinnerModel model = new SpinnerNumberModel(intParameter.getValue(), intParameter.getLowerLimit(), intParameter.getUpperLimit(), Math.max(1, (intParameter.getUpperLimit() + intParameter.getLowerLimit()) / 50));
				JSpinner spinnerModel = new JSpinner(model);
				spinnerModel.setPreferredSize(new Dimension(48, 28));
				spinnerModel.setOpaque(false);
				spinnerModel.setVisible(true);
				gridSetter.nextCell().alignCenter();
				this.add(spinnerModel, gridSetter.get());
				spinners.add(spinnerModel);
			}
			algorithmParameters.add(parameter);
		}
	}
}
