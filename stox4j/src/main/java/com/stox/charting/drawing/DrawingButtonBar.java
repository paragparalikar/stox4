package com.stox.charting.drawing;

import com.stox.charting.ChartingView;
import com.stox.charting.drawing.Segment.SegmentState;

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
		getChildren().add(createSegmentButton());
	}
	
	private ToggleButton createSegmentButton() {
		final ToggleButton button = new ToggleButton();
		button.setToggleGroup(toggleGroup);
		button.setGraphic(new Line(2, 14, 14, 2));
		button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		button.setOnAction(event -> {
			if(button.isSelected()) {
				new DrawingModeMouseHandler(
						chartingView.getPriceChart(), 
						() -> button.setSelected(false),
						chart -> new SegmentState().create(chart))
				.addListeners();
			}
		});
		return button;
	}
	
}
