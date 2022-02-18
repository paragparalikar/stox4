package com.stox.charting;

import java.util.concurrent.ExecutorService;

import com.stox.charting.axis.XAxis;
import com.stox.charting.chart.Chart;
import com.stox.charting.plot.Plot;
import com.stox.charting.plot.PricePlot;
import com.stox.charting.tools.RulesButton;
import com.stox.common.bar.BarService;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class ChartingView extends BorderPane {

	private final XAxis xAxis;
	private final ChartingContext context;
	private final Chart priceChart = new Chart();
	private final ToolBar toolBar = new ToolBar();
	private final SplitPane splitPane = new SplitPane();
	private final ObservableList<Chart> charts = FXCollections.observableArrayList();
	
	public ChartingView(ChartingContext context, BarService barService, ExecutorService executor) {
		this.context = context;
		this.xAxis = new XAxis(context);
		
		add(priceChart);
		add(new PricePlot(barService, executor));
		
		setCenter(splitPane);
		setBottom(new VBox(xAxis, toolBar));
		toolBar.getItems().add(new RulesButton(this, context));
	}
	
	public void add(Chart chart) {
		chart.setXAxis(xAxis);
		chart.setContext(context);
		charts.add(chart);
		splitPane.getItems().add(chart);
	}
	
	public void add(Plot<?> plot) {
		priceChart.add(plot);
	}
	
}
