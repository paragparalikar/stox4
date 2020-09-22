package com.stox.module.charting.drawing.segment;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.stox.module.charting.ChartingView;
import com.stox.module.charting.drawing.AbstractDrawingToggleButton;
import com.stox.module.charting.event.UpdatableRequestEvent;

import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.scene.control.ContentDisplay;
import javafx.scene.input.MouseEvent;

public abstract class SegmentToggleButton<T extends Segment<?>> extends AbstractDrawingToggleButton {

	public SegmentToggleButton(ChartingView chartingView) {
		super(chartingView);
		getStyleClass().addAll("icon", "primary");
		setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		selectedProperty().addListener(this);
	}

	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		super.changed(observable, oldValue, newValue);
		Optional.ofNullable(getChartingView())
				.ifPresent(chartingView -> chartingView.mouseModeHandler(new SegmentModeMouseHandler(chartingView, buildSegmentModeMouseEventHandler(this::onStart, this::onEnd))));
	}

	private void onStart(T segment, MouseEvent event) {
		add(segment, event.getScreenX(), event.getScreenY());
		final Point2D point = segment.getNode().getParent().screenToLocal(event.getScreenX(), event.getScreenY());
		move(segment, point);
		segment.getNode().fireEvent(new UpdatableRequestEvent(segment));
	}

	private void onEnd(T segment) {
		setSelected(false);
		getChartingView().mouseModeHandler(null);
	}

	protected abstract SegmentModeMouseEventHandler<T> buildSegmentModeMouseEventHandler(final BiConsumer<T, MouseEvent> startCallback, final Consumer<T> endCallback);

	protected abstract void move(final T segment, final Point2D point);

}
