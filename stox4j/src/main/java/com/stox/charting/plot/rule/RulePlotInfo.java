package com.stox.charting.plot.rule;

import com.stox.charting.plot.PlotInfo;

import javafx.scene.Node;
import javafx.scene.control.Label;

public class RulePlotInfo extends PlotInfo<Boolean> {
	private final Label nameLabel = new Label();
	private final Label valueLabel = new Label();

	public RulePlotInfo(Node node) {
		super(node);
		nameLabel.getStyleClass().add("plot-name");
		getStyleClass().add("plot-info-pane");
		getChildren().addAll(nameLabel, valueLabel);
		
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