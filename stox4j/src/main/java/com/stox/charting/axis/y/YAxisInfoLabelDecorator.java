package com.stox.charting.axis.y;

import com.stox.charting.ChartingContext;
import com.stox.charting.crosshair.Crosshair;
import com.stox.common.util.Strings;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class YAxisInfoLabelDecorator {

	public YAxis decorate(YAxis yAxis, ChartingContext context, Crosshair crosshair) {
		final Label label = new Label();
		label.getStyleClass().add("axis-info-label");
		label.visibleProperty().bind(crosshair.visibleProperty());
		yAxis.getChildren().add(new Pane(label));
		context.getBarSeriesProperty().addListener((o,old,value) -> updateCrosshairLabel(label, yAxis, crosshair));
		crosshair.getHorizontalLine().endYProperty().addListener((o,old,value) -> updateCrosshairLabel(label, yAxis, crosshair));
		return yAxis;
	}
	
	private void updateCrosshairLabel(Label label, YAxis yAxis, Crosshair crosshair) {
		final double value = crosshair.getHorizontalLine().getEndY();
		label.setText(Strings.toString(yAxis.getValue(value)));
		label.setLayoutY(value - label.getHeight()/2);
	}
	
}