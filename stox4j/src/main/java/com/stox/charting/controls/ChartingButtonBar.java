package com.stox.charting.controls;

import java.util.Optional;

import com.stox.charting.ChartingView;
import com.stox.charting.axis.x.XAxis;

import javafx.beans.binding.DoubleBinding;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;

public class ChartingButtonBar extends HBox {

	public ChartingButtonBar(ChartingView chartingView) {
		setManaged(false);
		bind(chartingView);
		createZoomControls(chartingView);
		createPanControls(chartingView);
		createDataControls(chartingView);
		getStyleClass().add("charting-control-buttons");
	}
	
	private void bind(ChartingView chartingView) {
		parentProperty().addListener((o,old,parent) -> {
			Optional.ofNullable(parent).ifPresent(container -> {
				if(Region.class.isAssignableFrom(container.getClass())) {
					final Region region = Region.class.cast(parent);
					bindLayoutX(region);
					bindLayoutY(region, chartingView);
				}
			});
		});
	}
	
	private void bindLayoutX(Region container) {
		layoutXProperty().unbind();
		layoutXProperty().bind(new DoubleBinding() {
			{bind(widthProperty(), container.widthProperty());}
			protected double computeValue() {
				return container.getWidth() / 2 - getWidth() / 2 - 64;
			}
		});
	}
	
	private void bindLayoutY(Region container, ChartingView chartingView) {
		layoutYProperty().unbind();
		final XAxis xAxis = chartingView.getXAxis();
		layoutYProperty().bind(new DoubleBinding() {
			{bind(heightProperty(), container.heightProperty(), xAxis.heightProperty());}
			protected double computeValue() {
				return container.getHeight() - getHeight() - xAxis.getHeight() - 30;
			}
		});
	}
	private void createZoomControls(ChartingView chartingView) {
		final HBox zoomButtonBar = new HBox();
		zoomButtonBar.getStyleClass().add("charting-control-button-bar");
		zoomButtonBar.getChildren().addAll(new ZoomInButton(chartingView), new ZoomOutButton(chartingView));
		getChildren().add(zoomButtonBar);
	}
	
	private void createPanControls(ChartingView chartingView) {
		final HBox panButtonBar = new HBox();
		panButtonBar.getStyleClass().add("charting-control-button-bar");
		panButtonBar.getChildren().addAll(new PanLeftButton(chartingView), 
				new PanRightButton(chartingView), new PanResetButton(chartingView));
		getChildren().add(panButtonBar);
	}
	
	private void createDataControls(ChartingView chartingView) {
		final HBox dataButtonBar = new HBox();
		dataButtonBar.getStyleClass().add("charting-control-button-bar");
		dataButtonBar.getChildren().addAll(new RefreshButton(chartingView));
		getChildren().add(dataButtonBar);
	}

}
