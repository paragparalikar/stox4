package com.stox.charting;

import java.util.List;
import java.util.stream.Collectors;

import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;
import com.stox.charting.handler.CompositeModeMouseHandler;
import com.stox.charting.handler.pan.PanModeMouseHandler;
import com.stox.charting.handler.zoom.ZoomModeMouseHandler;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Chart extends BorderPane {

	private final HBox verticalAxes = new HBox();
	private final ObservableList<Plot<?>> plots = FXCollections.observableArrayList();
	private final StackPane contentArea = new StackPane();
	
	public Chart(Plot<?>...plots) {
		setRight(verticalAxes);
		setCenter(contentArea);
		this.plots.addListener(this::changed);
		this.plots.addAll(plots);
		
		final PanModeMouseHandler panModeMouseHandler = new PanModeMouseHandler();
		final ZoomModeMouseHandler zoomModeMouseHandler = new ZoomModeMouseHandler();
		final CompositeModeMouseHandler compositeModeMouseHandler = new CompositeModeMouseHandler(panModeMouseHandler, zoomModeMouseHandler);
		compositeModeMouseHandler.attach(contentArea);
		contentArea.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
	}
	
	private void changed(Change<? extends Plot<?>> change) {
		final List<YAxis> axes = this.plots.stream().map(Plot::getYAxis).collect(Collectors.toList());
		verticalAxes.getChildren().setAll(axes);
		contentArea.getChildren().setAll(plots);
	}
	
	public void layoutChildren(XAxis xAxis) {
		// verticalAxis.layoutChildren
		final double width = contentArea.getWidth();
		final double height = contentArea.getHeight();
		if(0 < width && 0 < height) {
			for(Plot<?> plot : plots) {
				plot.layoutChildren(xAxis, height, width);
			}
		}
	}
	
}
