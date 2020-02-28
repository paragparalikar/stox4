package com.stox.module.charting.drawing.segment.vertical;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.stox.module.charting.drawing.segment.SegmentModeMouseEventHandler;

import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;

public class VerticalSegmentModeMouseEventHandler extends SegmentModeMouseEventHandler<VerticalSegment> {

	public VerticalSegmentModeMouseEventHandler(BiConsumer<VerticalSegment, MouseEvent> startCallback,
			Consumer<VerticalSegment> endCallback) {
		super(startCallback, endCallback);
	}

	@Override
	protected VerticalSegment buildSegment() {
		return new VerticalSegment();
	}

	@Override
	protected void move(VerticalSegment segment, Point2D startPoint, Point2D endPoint) {
		segment.move(endPoint.getX(), startPoint.getY(), endPoint.getY());
	}

}
