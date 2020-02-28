package com.stox.module.charting.drawing.segment.vertical;

import com.stox.module.charting.drawing.ControlPoint;
import com.stox.module.charting.drawing.segment.Segment;

public class VerticalSegment extends Segment<VerticalSegmentState> {

	protected void bind() {
		super.bind();
		final ControlPoint one = getOne();
		final ControlPoint two = getTwo();
		one.centerXProperty().bindBidirectional(two.centerXProperty());
	}

	public void move(final double deltaX, final double deltaY) {
		final ControlPoint one = getOne();
		final ControlPoint two = getTwo();
		move(one.getCenterX() + deltaX, one.getCenterY() + deltaY, two.getCenterY() + deltaY);
	}

	public void move(final double x, final double startY, final double endY) {
		final ControlPoint one = getOne();
		final ControlPoint two = getTwo();
		one.setCenterX(x);
		one.setCenterY(startY);
		two.setCenterX(x);
		two.setCenterY(endY);
	}

	@Override
	public VerticalSegmentState state() {
		return fill(new VerticalSegmentState());
	}

}
