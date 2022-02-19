package com.stox.charting;

import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;
import com.stox.charting.grid.HorizontalGrid;
import com.stox.charting.handler.CompositeModeMouseHandler;
import com.stox.charting.handler.pan.PanModeMouseHandler;
import com.stox.charting.handler.zoom.ZoomModeMouseHandler;
import com.stox.charting.plot.Plot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import lombok.Getter;
import lombok.Setter;

public class Chart extends BorderPane {

	@Getter @Setter private XAxis xAxis;
	@Getter @Setter private ChartingContext context;
	private final HorizontalGrid horizontalGrid = new HorizontalGrid();
	private final YAxis yAxis = new YAxis(horizontalGrid);
	private final StackPane contentArea = new StackPane(horizontalGrid);
	private final ObservableList<Plot<?>> plots = FXCollections.observableArrayList();
	private final PanModeMouseHandler panModeMouseHandler = new PanModeMouseHandler();
	private final ZoomModeMouseHandler zoomModeMouseHandler = new ZoomModeMouseHandler();
	private final CompositeModeMouseHandler compositeModeMouseHandler = new CompositeModeMouseHandler(panModeMouseHandler, zoomModeMouseHandler);
	
	public Chart() {
		setRight(yAxis);
		setCenter(contentArea);
		compositeModeMouseHandler.attach(contentArea);
		final Background background = new Background(new BackgroundFill(Color.TRANSPARENT, null, null));
		setBackground(background);
		contentArea.setBackground(background);
	}
	
	public boolean hasSize() {
		return 0 < contentArea.getHeight() && 0 < contentArea.getWidth();
	}
	
	public void redraw() {
		if(hasSize()) {
			yAxis.reset();
			for(Plot<?> plot : plots) plot.layoutChartChildren();
			yAxis.layoutChartChildren();
		}
	}
	
	public void add(Plot<?> plot) {
		plot.setXAxis(xAxis);
		plot.setYAxis(yAxis);
		plot.setContext(context);
		plots.add(plot);
		contentArea.getChildren().add(plot);
	}
	
}
