package com.stox.module.charting.drawing.segment.trend;

import com.stox.module.charting.drawing.segment.Segment;
import com.stox.module.charting.drawing.segment.SegmentState;

public class TrendSegmentState extends SegmentState{
	public static final String TYPE = "segment-trend";

	public TrendSegmentState() {
		super(TYPE);
	}

	@Override
	public Segment<TrendSegmentState> drawing() {
		return new TrendSegment().state(this);
	}

}
