package com.stox.charting.chart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Set;

import com.stox.charting.ChartingView;
import com.stox.charting.axis.y.YAxis;
import com.stox.charting.axis.y.YAxisGridDecorator;
import com.stox.charting.axis.y.YAxisInfoLabelDecorator;
import com.stox.charting.axis.y.YAxisRedrawRequestEvent;
import com.stox.charting.axis.y.YAxisValueLabelDecorator;
import com.stox.charting.drawing.Drawing;
import com.stox.charting.grid.HorizontalGrid;
import com.stox.charting.handler.CompositeModeMouseHandler;
import com.stox.charting.handler.pan.PanModeMouseHandler;
import com.stox.charting.handler.zoom.ZoomModeMouseHandler;
import com.stox.charting.plot.Plot;
import com.stox.charting.plot.PlotInfo;
import com.stox.common.ui.NoLayoutPane;

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
	private static final Color[] COLORS = {Color.BLACK, Color.BLUEVIOLET, Color.BROWN, Color.DARKBLUE, Color.DARKCYAN, Color.BLACK, Color.DARKGREEN};
	
	private final YAxis yAxis = new YAxis();
	private final ChartingView chartingView;
	private final VBox infoPane = new VBox();
	private final HorizontalGrid horizontalGrid = new HorizontalGrid();
	private final Pane plotsContainer = new NoLayoutPane();
	private final Pane widgetsContainer = new Pane(infoPane);
	private final StackPane contentArea = new StackPane(horizontalGrid, plotsContainer, widgetsContainer);
	private final ObservableList<Plot<?,?,?>> plots = FXCollections.observableArrayList();
	private final Set<Drawing<?>> drawings = Collections.newSetFromMap(new IdentityHashMap<>());
	private final CompositeModeMouseHandler modeMouseHandler = new CompositeModeMouseHandler(
			new PanModeMouseHandler(this), 
			new ZoomModeMouseHandler(this));
	
	public Chart(ChartingView chartingView) {
		this.chartingView = chartingView;
		style();
		decorate();
		setRight(yAxis);
		setCenter(contentArea);
		
		modeMouseHandler.addListeners();
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
			for(Drawing<?> drawing : drawings) drawing.draw();
			yAxis.fireEvent(new YAxisRedrawRequestEvent());
		}
	}
	
	public void add(Plot<?, ?, ?> plot) {
		plot.setChart(this);
		plots.add(plot);
		plotsContainer.getChildren().add(plot);
		final PlotInfo<?> plotInfo = plot.getInfo();
		infoPane.getChildren().add(plotInfo);
		plot.setColor(COLORS[plots.size() - 1]);
		plot.reload();
	}
	
	public void removePlot(Plot<?, ?, ?> plot) {
		plots.remove(plot);
		plotsContainer.getChildren().remove(plot);
		infoPane.getChildren().remove(plot.getInfo());
		if(plots.isEmpty()) chartingView.remove(this);
	}
	
	public void add(Drawing<?> drawing) {
		if(drawings.add(drawing)) widgetsContainer.getChildren().add(drawing.getNode());
	}
	
	public void remove(Drawing<?> drawing) {
		drawings.remove(drawing);
		widgetsContainer.getChildren().remove(drawing.getNode());
	}
	
	public void clearDrawings() {
		final List<Drawing<?>> drawings = new ArrayList<>(this.drawings);
		drawings.forEach(this::remove);
	}
}
