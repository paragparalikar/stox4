package com.stox.module.charting.drawing.segment.trend;

import com.stox.module.charting.drawing.ControlPoint;
import com.stox.module.charting.drawing.segment.Segment;

public class TrendSegment extends Segment {

	@Override
	public void move(final double xDelta, final double yDelta) {
		final ControlPoint one = getOne();
		final ControlPoint two = getTwo();
		move(one.getCenterX() + xDelta, one.getCenterY() + yDelta, two.getCenterX() + xDelta, two.getCenterY() + yDelta);
	}

	public void move(final double startX, final double startY, final double endX, final double endY) {
		final ControlPoint one = getOne();
		final ControlPoint two = getTwo();
		one.setCenterX(startX);
		one.setCenterY(startY);
		two.setCenterX(endX);
		two.setCenterY(endY);
	}

}
