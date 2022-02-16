package com.stox.charting.chart;

import com.stox.charting.ChartingContext;
import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;
import com.stox.charting.handler.CompositeModeMouseHandler;
import com.stox.charting.handler.pan.PanModeMouseHandler;
import com.stox.charting.handler.zoom.ZoomModeMouseHandler;
import com.stox.charting.plot.Plot;
import com.stox.common.scrip.Scrip;
import com.stox.common.util.MathUtil;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class Chart extends BorderPane {

	private final ChartingContext context;
	private final YAxis yAxis = new YAxis();
	private final StackPane contentArea = new StackPane();
	private final ObservableList<Plot<?>> plots = FXCollections.observableArrayList();
	
	public Chart(ChartingContext context) {
		setRight(yAxis);
		setCenter(contentArea);
		this.context = context;

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
			for(Plot<?> plot : plots) plot.reload(scrip, xAxis);
			redraw(xAxis, width, height);
		}
	}
	
	public void redraw(XAxis xAxis, double width, double height) {
		yAxis.reset();
		final int barCount = context.getBarSeries().getBarCount();
		final int startIndex = MathUtil.clip(0, xAxis.getStartIndex(), barCount);
		final int endIndex = MathUtil.clip(0, xAxis.getEndIndex(), barCount);
		for(Plot<?> plot : plots) plot.updateYAxis(startIndex, endIndex, yAxis);
		for(Plot<?> plot : plots) plot.layoutChildren(xAxis, yAxis, startIndex, endIndex, height, width);
	}
	
	public void add(Plot<?> plot) {
		plots.add(plot);
		contentArea.getChildren().add(plot);
	}
	
}
