package com.stox.module.charting.drawing.region;

import com.stox.module.charting.ChartingView;
import com.stox.module.charting.drawing.AbstractDrawingToggleButton;
import com.stox.module.charting.event.UpdatableRequestEvent;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.shape.Rectangle;

public class ChartRegionToggleButton extends AbstractDrawingToggleButton
		implements  ChangeListener<Boolean> {

	public ChartRegionToggleButton(ChartingView chartingView) {
		super(chartingView);
		classes("icon", "primary", "chart-region-toggle-button");
		setGraphic(new Rectangle(2, 2, 14, 14));
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		selectedProperty().addListener(this);
		setTooltip(new Tooltip("Region"));
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		super.changed(observable, oldValue, newValue);
		final ChartingView chartingView = getChartingView();
		if (newValue && null != chartingView) {
			final ChartRegionModeMouseHandler modeMouseHandler = new ChartRegionModeMouseHandler(chartingView,
					new ChartRegionModeMouseEventHandler((region, event) -> {
						add(region, event.getScreenX(), event.getScreenY());
						final Point2D point = region.getNode().getParent().screenToLocal(event.getScreenX(),
								event.getScreenY());
						region.move(point.getX(), point.getY(), point.getX(), point.getY());
						region.getNode().fireEvent(new UpdatableRequestEvent(region));
					}, region -> {
						setSelected(false);
						chartingView.mouseModeHandler(null);
					}));
			chartingView.mouseModeHandler(modeMouseHandler);
		}
	}

}
