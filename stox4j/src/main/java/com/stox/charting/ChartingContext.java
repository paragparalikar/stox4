package com.stox.charting;

import org.ta4j.core.BarSeries;

import com.stox.common.scrip.Scrip;

import lombok.Data;

@Data
public class ChartingContext {

	private Scrip scrip;
	private BarSeries barSeries;

}
