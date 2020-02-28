package com.stox.module.charting.drawing.segment.horizontal;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.stox.module.charting.drawing.segment.SegmentModeMouseEventHandler;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class HorizontalSegmentModeMouseEventHandler extends SegmentModeMouseEventHandler<HorizontalSegment> {

	public HorizontalSegmentModeMouseEventHandler(BiConsumer<HorizontalSegment, MouseEvent> startCallback,
			Consumer<HorizontalSegment> endCallback) {
		super(startCallback, endCallback);
	}

	@Override
	protected HorizontalSegment buildSegment() {
		return new HorizontalSegment();
	}

	@Override
	protected void move(HorizontalSegment segment, Point2D startPoint, Point2D endPoint) {
		segment.move(endPoint.getY(), startPoint.getX(), endPoint.getX());
	}

}
