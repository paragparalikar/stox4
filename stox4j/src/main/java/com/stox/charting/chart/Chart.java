package com.stox.charting.chart;

import com.stox.charting.ChartingView;
import com.stox.charting.axis.y.YAxis;
import com.stox.charting.axis.y.YAxisGridDecorator;
import com.stox.charting.axis.y.YAxisInfoLabelDecorator;
import com.stox.charting.axis.y.YAxisRedrawRequestEvent;
import com.stox.charting.axis.y.YAxisValueLabelDecorator;
import com.stox.charting.grid.HorizontalGrid;
import com.stox.charting.handler.CompositeModeMouseHandler;
import com.stox.charting.handler.pan.PanModeMouseHandler;
import com.stox.charting.handler.zoom.ZoomModeMouseHandler;
import com.stox.charting.plot.Plot;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Cursor;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import lombok.Getter;

@Getter
public class Chart extends BorderPane {
	private static final Color[] COLORS = {Color.BLUEVIOLET, Color.BROWN, Color.DARKBLUE, Color.DARKCYAN, Color.BLACK, Color.DARKGREEN};
	
	
	private final YAxis yAxis = new YAxis();
	private final ChartingView chartingView;
	private final VBox infoPane = new VBox();
	private final HorizontalGrid horizontalGrid = new HorizontalGrid();
	private final StackPane contentArea = new StackPane(horizontalGrid, infoPane);
	private final ObservableList<Plot<?,?,?>> plots = FXCollections.observableArrayList();
	private final PanModeMouseHandler panModeMouseHandler = new PanModeMouseHandler();
	private final ZoomModeMouseHandler zoomModeMouseHandler = new ZoomModeMouseHandler();
	private final CompositeModeMouseHandler compositeModeMouseHandler = new CompositeModeMouseHandler(panModeMouseHandler, zoomModeMouseHandler);
	
	public Chart(ChartingView chartingView) {
		this.chartingView = chartingView;
		style();
		decorate();
		setRight(yAxis);
		setCenter(contentArea);
		compositeModeMouseHandler.attach(contentArea);
		contentArea.widthProperty().addListener((o,old,value) -> redraw());
		contentArea.heightProperty().addListener((o,old,value) -> redraw());
	}
	
	private void decorate() {
		final Pane yAxisContent = new Pane();
		new YAxisValueLabelDecorator().decorate(yAxis, yAxisContent);
		new YAxisGridDecorator().decorate(yAxis, yAxisContent, horizontalGrid);
		new YAxisInfoLabelDecorator().decorate(yAxis, chartingView.getContext(), chartingView.getCrosshair());
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
			for(Plot<?, ?, ?> plot : plots) plot.layoutChartChildren();
			yAxis.fireEvent(new YAxisRedrawRequestEvent());
		}
	}
	
	public void add(Plot<?, ?, ?> plot) {
		plot.setChart(this);
		plots.add(plot);
		contentArea.getChildren().add(plot);
		infoPane.getChildren().add(plot.getInfo());
		infoPane.toFront();
		plot.getUnitParent().setColor(COLORS[plots.size() - 1]);
		plot.reload();
	}
	
	public void removePlot(Plot<?, ?, ?> plot) {
		plots.remove(plot);
		contentArea.getChildren().remove(plot);
		infoPane.getChildren().remove(plot.getInfo());
		if(plots.isEmpty()) chartingView.remove(this);
	}
	
}
