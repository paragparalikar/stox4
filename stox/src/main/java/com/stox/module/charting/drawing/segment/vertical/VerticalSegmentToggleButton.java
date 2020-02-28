package com.stox.module.charting.drawing.segment.vertical;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.stox.module.charting.ChartingView;
import com.stox.module.charting.drawing.segment.SegmentModeMouseEventHandler;
import com.stox.module.charting.drawing.segment.SegmentToggleButton;

import javafx.geometry.Point2D;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

public class VerticalSegmentToggleButton extends SegmentToggleButton<VerticalSegment> {

	public VerticalSegmentToggleButton(ChartingView chartingView) {
		super(chartingView);
		setGraphic(new Line(8, 2, 8, 14));
		setTooltip(new Tooltip("Vertical Segment"));
	}
	
	@Override
	protected SegmentModeMouseEventHandler<VerticalSegment> buildSegmentModeMouseEventHandler(
			BiConsumer<VerticalSegment, MouseEvent> startCallback, Consumer<VerticalSegment> endCallback) {
		return new VerticalSegmentModeMouseEventHandler(startCallback, endCallback);
	}

	@Override
	protected void move(VerticalSegment segment, Point2D point) {
		segment.move(point.getX(), point.getY(), point.getY());
	}

}
