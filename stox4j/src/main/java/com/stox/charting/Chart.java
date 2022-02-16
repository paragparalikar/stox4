package com.stox.charting;

import com.stox.charting.axis.XAxis;
import com.stox.charting.handler.CompositeModeMouseHandler;
import com.stox.charting.handler.pan.PanModeMouseHandler;
import com.stox.charting.handler.zoom.ZoomModeMouseHandler;
import com.stox.common.scrip.Scrip;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Chart extends BorderPane {

	private final HBox verticalAxes = new HBox();
	private final StackPane contentArea = new StackPane();
	private final ObservableList<Plot<?>> plots = FXCollections.observableArrayList();
	
	public Chart() {
		setRight(verticalAxes);
		setCenter(contentArea);
		
		final PanModeMouseHandler panModeMouseHandler = new PanModeMouseHandler();
		final ZoomModeMouseHandler zoomModeMouseHandler = new ZoomModeMouseHandler();
		final CompositeModeMouseHandler compositeModeMouseHandler = new CompositeModeMouseHandler(panModeMouseHandler, zoomModeMouseHandler);
		compositeModeMouseHandler.attach(contentArea);
		contentArea.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
	}
	
	public void reload(Scrip scrip, XAxis xAxis) {
		final double width = contentArea.getWidth();
		final double height = contentArea.getHeight();
		if(0 < width && 0 < height) {
			for(Plot<?> plot : plots) {
				plot.reload(scrip, xAxis);
				plot.layoutChildren(xAxis, height, width);
			}
		}
	}
	
	public void add(Plot<?> plot) {
		plots.add(plot);
		contentArea.getChildren().add(plot);
		verticalAxes.getChildren().add(plot.getYAxis());
	}
	
}
