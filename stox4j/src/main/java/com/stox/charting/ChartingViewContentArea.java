package com.stox.charting;

import com.stox.charting.axis.XAxis;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.control.SplitPane;

public class ChartingViewContentArea extends SplitPane {

	private final ObservableList<Chart> charts;
	
	public ChartingViewContentArea(ObservableList<Chart> charts) {
		this.charts = charts;
		getItems().setAll(charts);
		charts.addListener(this::onChanged);
	}

	public void onChanged(Change<? extends Chart> c) {
		getItems().setAll(charts);
	}
	
	public void layoutCharts(XAxis xAxis) {
		for(Chart chart : charts) {
			chart.layoutChildren(xAxis);
		}
	}

}
