package com.stox.charting.drawing;

import java.util.function.Function;

import com.stox.charting.ChartingView;
import com.stox.charting.chart.Chart;
import com.stox.charting.drawing.HorizontalSegment.HorizontalSegmentState;
import com.stox.charting.drawing.Segment.SegmentState;
import com.stox.charting.drawing.VerticalSegment.VerticalSegmentState;

import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;

public class DrawingButtonBar extends HBox {
	
	private final ChartingView chartingView;
	private final ToggleGroup toggleGroup = new ToggleGroup();

	public DrawingButtonBar(ChartingView chartingView) { 
		this.chartingView = chartingView;
		getChildren().addAll(
				createButton(new Line(2, 14, 14, 2), chart -> new SegmentState().create(chart)),
				createButton(new Line(8, 2, 8, 14), chart -> new VerticalSegmentState().create(chart)),
				createButton(new Line(2, 8, 14, 8), chart -> new HorizontalSegmentState().create(chart)));
	}
	
	private ToggleButton createButton(Node graphics, Function<Chart, Drawing<?>> function) {
		final ToggleButton button = new ToggleButton();
		button.setToggleGroup(toggleGroup);
		button.setGraphic(graphics);
		button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		button.setOnAction(event -> {
			if(button.isSelected()) {
				if(0 < chartingView.getContext().getBarCount()) {
					new DrawingModeMouseHandler(
							chartingView.getPriceChart(), 
							() -> button.setSelected(false),
							function)
					.addListeners();
				} else {
					button.setSelected(false);
				}
			}
		});
		return button;
	}
	
}
