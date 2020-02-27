package com.stox.module.charting.plot;

import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class DerivativePlotState extends PlotState {

	@SerializedName("underlay")
	private Underlay underlay;

}
