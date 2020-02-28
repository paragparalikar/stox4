package com.stox.module.charting.drawing.segment.horizontal;

import com.stox.module.charting.drawing.ControlPoint;
import com.stox.module.charting.drawing.segment.Segment;

public class HorizontalSegment extends Segment<HorizontalSegmentState> {

	protected void bind() {
		super.bind();
		final ControlPoint one = getOne();
		final ControlPoint two = getTwo();
		one.centerYProperty().bindBidirectional(two.centerYProperty());
	}

	public void move(final double deltaX, final double deltaY) {
		final ControlPoint one = getOne();
		final ControlPoint two = getTwo();
		move(one.getCenterY() + deltaY, one.getCenterX() + deltaX, two.getCenterX() + deltaX);
	}

	public void move(final double y, final double startX, final double endX) {
		final ControlPoint one = getOne();
		final ControlPoint two = getTwo();
		one.setCenterX(startX);
		one.setCenterY(y);
		two.setCenterX(endX);
		two.setCenterY(y);
	}

	@Override
	public HorizontalSegmentState state() {
		return fill(new HorizontalSegmentState());
	}

}
