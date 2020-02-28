package com.stox.module.charting.drawing.text;

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
public class ChartTextState extends DrawingState{
	public static final String TYPE = "chart-text";

	@SerializedName("text")
	private String text;
	
	@SerializedName("location")
	private Location location;
	
	public ChartTextState() {
		super(TYPE);
	}

	@Override
	public Drawing<ChartTextState> drawing() {
		return new ChartText().state(this);
	}

}
