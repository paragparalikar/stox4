package com.stox.module.charting.plot.price;

import com.google.gson.annotations.SerializedName;
import com.stox.module.charting.unit.PriceUnitType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(fluent = true)
@EqualsAndHashCode(callSuper = true)
public class PrimaryPricePlotState extends PricePlotState {

	@SerializedName("priceUnitType")
	private PriceUnitType priceUnitType;
	
}
