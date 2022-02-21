package com.stox.charting.plot;

import com.stox.common.ui.Icon;

import javafx.scene.control.Label;

public class DefaultPlotInfo<T> extends PlotInfo<T> {
	
	private final Label valueLabel = new Label();

	public DefaultPlotInfo(Plot<T> plot) {
		super(plot);
		getChildren().add(valueLabel);
		createButton(Icon.TRASH, event -> plot.getChart().removePlot(plot));
	}
	
	@Override
	public void setValue(T model) {
		valueLabel.setVisible(null != model);
		valueLabel.setText(null == model ? null : model.toString().toUpperCase());
	}

}
