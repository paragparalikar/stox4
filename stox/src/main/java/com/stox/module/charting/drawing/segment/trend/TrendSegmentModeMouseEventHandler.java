package com.stox.module.charting.drawing.segment.trend;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.stox.module.charting.drawing.segment.SegmentModeMouseEventHandler;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class TrendSegmentModeMouseEventHandler extends SegmentModeMouseEventHandler<TrendSegment> {

	public TrendSegmentModeMouseEventHandler(BiConsumer<TrendSegment, MouseEvent> startCallback,
			Consumer<TrendSegment> endCallback) {
		super(startCallback, endCallback);
	}

	@Override
	protected TrendSegment buildSegment() {
		return new TrendSegment();
	}

	@Override
	protected void move(final TrendSegment segment, final Point2D startPoint, final Point2D endPoint) {
		segment.move(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
	}

}
