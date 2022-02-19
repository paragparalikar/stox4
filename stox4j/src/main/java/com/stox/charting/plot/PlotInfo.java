package com.stox.charting.plot;

import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class PlotInfo<T> extends HBox {

	private final Label name = new Label();
	
	public PlotInfo() {
		getStyleClass().add("plot-info-pane");
		name.getStyleClass().add("plot-name");
		getChildren().add(name);
	}
	
	public void setName(String value) {
		setVisible(null != value);
		name.setText(null == value ? null : value.toUpperCase());
	}
	
	public void setValue(T model) {
		
	}
	
}
