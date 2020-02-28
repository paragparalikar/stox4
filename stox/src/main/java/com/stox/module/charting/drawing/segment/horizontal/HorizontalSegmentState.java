package com.stox.module.charting.drawing.segment.horizontal;

import com.stox.module.charting.drawing.segment.Segment;
import com.stox.module.charting.drawing.segment.SegmentState;

public class HorizontalSegmentState extends SegmentState {
	public static final String TYPE = "segment-horizontal";

	public HorizontalSegmentState() {
		super(TYPE);
	}

	@Override
	public Segment<HorizontalSegmentState> drawing() {
		return new HorizontalSegment().state(this);
	}

}
