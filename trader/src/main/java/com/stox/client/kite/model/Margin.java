package com.stox.client.kite.model;

import lombok.Data;

@Data
public class Margin {

	private SegmentMargin equity;
	
	private SegmentMargin commodity;
	
}
