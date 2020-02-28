package com.stox.module.charting.drawing.segment;

import com.google.gson.annotations.SerializedName;
import com.stox.module.charting.drawing.DrawingState;
import com.stox.module.charting.drawing.Location;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public abstract class SegmentState extends DrawingState {

	@SerializedName("one")
	private Location one;
	
	@SerializedName("two")
	private Location two;
	
	public SegmentState(@NonNull final String type) {
		super(type);
	}
	
}
