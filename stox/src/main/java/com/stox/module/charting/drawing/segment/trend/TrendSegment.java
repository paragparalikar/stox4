package com.stox.module.charting.drawing.segment.trend;

import com.stox.module.charting.drawing.ControlPoint;
import com.stox.module.charting.drawing.segment.Segment;

import lombok.Getter;

public class TrendSegment extends Segment {
	public static final String TYPE = "segment-trend"; 
	
	@Getter
	private String type = TYPE;

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
