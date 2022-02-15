package com.stox.charting;

import com.stox.charting.axis.XAxis;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Chart extends BorderPane {

	private final VBox verticalAxis = new VBox();
	private final HBox horizontalAxis = new HBox();
	private final ObservableList<Plot<?>> plots = FXCollections.observableArrayList();
	private final ChartContentArea contentArea = new ChartContentArea(plots);
	
	public Chart(Plot<?>...plots) {
		setRight(verticalAxis);
		setBottom(horizontalAxis);
		setCenter(contentArea);
		this.plots.addAll(plots);
	}
	
	public void layoutChildren(XAxis xAxis) {
		// verticalAxis.layoutChildren
		// horizontalAxis.layoutChildren
		contentArea.layoutChildren(xAxis);
	}
	
}
