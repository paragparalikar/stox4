package com.stox.charting;

import com.stox.charting.axis.XAxis;

import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ChartContentArea extends StackPane {
	
	private final ObservableList<Plot<?>> plots;
	
	public ChartContentArea(ObservableList<Plot<?>> plots) {
		this.plots = plots;
		plots.addListener(this::onChanged);
		setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
		new PanAndZoomMouseHandler(this).attach();
	}
	
	public void layoutChildren(XAxis xAxis) {
		final double width = getWidth();
		final double height = getHeight();
		if(0 < width && 0 < height) {
			for(Plot<?> plot : plots) {
				plot.layoutChildren(xAxis, height, width);
			}
		}
	}

	public void onChanged(Change<? extends Plot<?>> change) {
		getChildren().setAll(plots);
	}

}
