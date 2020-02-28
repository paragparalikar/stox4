package com.stox.module.charting.drawing.region;

import com.google.gson.annotations.SerializedName;
import com.stox.module.charting.drawing.Drawing;
import com.stox.module.charting.drawing.DrawingState;
import com.stox.module.charting.drawing.Location;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class ChartRegionState extends DrawingState{
	public static final String TYPE = "chart-region";
	
	@SerializedName("one")
	private Location one;
	
	@SerializedName("two")
	private Location two;
	
	public ChartRegionState() {
		super(TYPE);
	}

	@Override
	public Drawing<ChartRegionState> drawing() {
		return new ChartRegion().state(this);
	}

}
