package com.stox.module.charting.drawing.segment.horizontal;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.stox.module.charting.ChartingView;
import com.stox.module.charting.drawing.segment.SegmentModeMouseEventHandler;
import com.stox.module.charting.drawing.segment.SegmentToggleButton;

import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class HorizontalSegmentToggleButton extends SegmentToggleButton<HorizontalSegment> {

	public HorizontalSegmentToggleButton(ChartingView chartingView) {
		super(chartingView);
		setGraphic(new Line(2, 8, 14, 8));
		setTooltip(new Tooltip("Horizontal Segment"));
	}
	
	@Override
	protected SegmentModeMouseEventHandler<HorizontalSegment> buildSegmentModeMouseEventHandler(
			BiConsumer<HorizontalSegment, MouseEvent> startCallback, Consumer<HorizontalSegment> endCallback) {
		return new HorizontalSegmentModeMouseEventHandler(startCallback, endCallback);
	}

	@Override
	protected void move(HorizontalSegment segment, Point2D point) {
		segment.move(point.getY(), point.getX(), point.getX());
	}

}
