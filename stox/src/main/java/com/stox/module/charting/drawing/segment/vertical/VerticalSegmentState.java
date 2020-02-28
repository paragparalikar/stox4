package com.stox.module.charting.drawing.segment.vertical;

import com.stox.module.charting.drawing.Drawing;
import com.stox.module.charting.drawing.segment.SegmentState;

public class VerticalSegmentState extends SegmentState {
	public static final String TYPE = "segment-vertical";
	
	public VerticalSegmentState() {
		super(TYPE);
	}

	@Override
	public Drawing<VerticalSegmentState> drawing() {
		return new VerticalSegment().state(this);
	}

}
