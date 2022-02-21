package com.stox.charting.chart;

import com.stox.charting.ChartingView.ChartingConfig;
import com.stox.charting.ChartingView.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;
import com.stox.charting.crosshair.Crosshair;
import com.stox.charting.grid.HorizontalGrid;
import com.stox.charting.handler.CompositeModeMouseHandler;
import com.stox.charting.handler.pan.PanModeMouseHandler;
import com.stox.charting.handler.zoom.ZoomModeMouseHandler;
import com.stox.charting.plot.Plot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Chart extends BorderPane {
	
	private final XAxis xAxis;
	private final YAxis yAxis;
	private final Crosshair crosshair;
	private final ChartingConfig config;
	private final ChartingContext context;
	private final VBox infoPane = new VBox();
	private final HorizontalGrid horizontalGrid = new HorizontalGrid();
	private final StackPane contentArea = new StackPane(horizontalGrid, infoPane);
	private final ObservableList<Plot<?>> plots = FXCollections.observableArrayList();
	private final PanModeMouseHandler panModeMouseHandler = new PanModeMouseHandler();
	private final ZoomModeMouseHandler zoomModeMouseHandler = new ZoomModeMouseHandler();
	private final CompositeModeMouseHandler compositeModeMouseHandler = new CompositeModeMouseHandler(panModeMouseHandler, zoomModeMouseHandler);
	
	@Builder
	public Chart(ChartingContext context, ChartingConfig config, Crosshair crosshair, XAxis xAxis) {
		this.xAxis = xAxis;
		this.config = config;
		this.context = context;
		this.crosshair = crosshair;
		this.yAxis = new YAxis(context, config, crosshair, horizontalGrid);
		setRight(yAxis);
		setCenter(contentArea);
		compositeModeMouseHandler.attach(contentArea);
		style();
	}
	
	private void style() {
		contentArea.setCursor(Cursor.CROSSHAIR);
		infoPane.getStyleClass().add("chart-info-pane");
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
		plot.setChart(this);
		plots.add(plot);
		contentArea.getChildren().add(plot);
		infoPane.getChildren().add(plot.getInfo());
		infoPane.toFront();
	}
	
	public void removePlot(Plot<?> plot) {
		plots.remove(plot);
		contentArea.getChildren().remove(plot);
		infoPane.getChildren().remove(plot.getInfo());
	}
	
}
