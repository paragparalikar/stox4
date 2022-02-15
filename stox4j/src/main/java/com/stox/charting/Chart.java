package com.stox.charting;

import java.util.List;
import java.util.stream.Collectors;

import com.stox.charting.axis.XAxis;
import com.stox.charting.axis.YAxis;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class Chart extends BorderPane {

	private final HBox verticalAxes = new HBox();
	private final ObservableList<Plot<?>> plots = FXCollections.observableArrayList();
	private final ChartContentArea contentArea = new ChartContentArea(plots);
	
	public Chart(Plot<?>...plots) {
		setRight(verticalAxes);
		setCenter(contentArea);
		this.plots.addListener(this::changed);
		this.plots.addAll(plots);
	}
	
	private void changed(Change<? extends Plot<?>> change) {
		final List<YAxis> axes = this.plots.stream().map(Plot::getYAxis).collect(Collectors.toList());
		verticalAxes.getChildren().setAll(axes);
	}
	
	public void layoutChildren(XAxis xAxis) {
		// verticalAxis.layoutChildren
		contentArea.layoutChildren(xAxis);
	}
	
}
