package com.stox.charting.plot;

import org.ta4j.core.num.Num;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.DefaultDialogx;
import com.stox.common.ui.Icon;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

public class DefaultPlotInfo<T> extends PlotInfo<T> {
	
	private final Label valueLabel = new Label();

	public DefaultPlotInfo(Plot<T, ?, ?> plot) {
		super(plot);
		getChildren().add(1, valueLabel);
		if(null != plot.getIndicatorConfig()) createButton(Icon.GEAR, event -> config());
		createButton(Icon.TIMES, event -> plot.getChart().removePlot(plot));
	}
	
	private void config() {
		final ConfigView configView = getPlot().createConfigView();
		new DefaultDialogx()
		.withTitle("Rules")
		.withContent(configView.getNode())
		.withButton(ButtonType.CANCEL)
		.withButton(ButtonType.APPLY, () -> {
			configView.populateModel();
			getPlot().reload();
		}).show(this);
	}
	
	@Override
	public void setValue(T model) {
		valueLabel.setVisible(null != model);
		valueLabel.setText(null == model ? null : toString(model));
	}
	
	private String toString(T model) {
		double value = 0;
		if(model instanceof Num) {
			value = ((Num) model).doubleValue();
		} else if (model instanceof Number) {
			value = ((Number) model).doubleValue();
		} else {
			return model.toString();
		}
		return String.format("%.2f", value);
	}

}
