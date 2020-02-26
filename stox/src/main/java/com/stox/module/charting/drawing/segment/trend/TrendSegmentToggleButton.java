package com.stox.module.charting.drawing.segment.trend;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.stox.module.charting.ChartingView;
import com.stox.module.charting.drawing.segment.SegmentModeMouseEventHandler;
import com.stox.module.charting.drawing.segment.SegmentToggleButton;

import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class TrendSegmentToggleButton extends SegmentToggleButton<TrendSegment> {

	public TrendSegmentToggleButton(ChartingView chartingView) {
		super(chartingView);
		setGraphic(new Line(2, 14, 14, 2));
		setTooltip(new Tooltip("Trend Segment"));
	}

	@Override
	protected SegmentModeMouseEventHandler<TrendSegment> buildSegmentModeMouseEventHandler(
			BiConsumer<TrendSegment, MouseEvent> startCallback, Consumer<TrendSegment> endCallback) {
		return new TrendSegmentModeMouseEventHandler(startCallback, endCallback);
	}

	@Override
	protected void move(TrendSegment segment, Point2D point) {
		segment.move(point.getX(), point.getY(), point.getX(), point.getY());
	}

}
