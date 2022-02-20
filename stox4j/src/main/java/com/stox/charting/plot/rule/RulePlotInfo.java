package com.stox.charting.plot.rule;

import com.stox.charting.plot.PlotInfo;
import com.stox.common.ui.Icon;

import javafx.scene.control.Label;

public class RulePlotInfo extends PlotInfo<Boolean> {
	private final Label nameLabel = new Label();
	private final Label valueLabel = new Label();

	public RulePlotInfo(RulePlot plot) {
		super(plot);
		nameLabel.getStyleClass().add("plot-name");
		getStyleClass().add("plot-info-pane");
		getChildren().addAll(nameLabel, valueLabel);
		createButton(Icon.TRASH, event -> plot.getChart().removePlot(plot));
	}
	
	public void setName(String value) {
		setVisible(null != value);
		nameLabel.setText(null == value ? null : value.toUpperCase());
	}

	@Override
	public void setValue(Boolean model) {
		valueLabel.setVisible(null != model);
		valueLabel.setText(null == model ? null : model.toString().toUpperCase());
	}
	
}