package com.stox.charting;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.layout.StackPane;

public class ChartContentArea extends StackPane {
	
	private final ObservableList<Plot<?>> plots;
	
	public ChartContentArea(ObservableList<Plot<?>> plots) {
		this.plots = plots;
		plots.addListener(this::onChanged);
	}
	
	public void layoutChildren(int startIndex, int endIndex) {
		final double width = getWidth();
		final double height = getHeight();
		if(0 < width && 0 < height) {
			for(Plot<?> plot : plots) {
				plot.layoutChildren(startIndex, endIndex, height, width);
			}
		}
	}

	public void onChanged(Change<? extends Plot<?>> change) {
		getChildren().setAll(plots);
	}

}
