package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.ChopIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.indicator.PlottableChopIndicator.ChopConfig;

import lombok.Data;

public class PlottableChopIndicator implements PlottableLineIndicator<ChopConfig> {

	@Data
	public static class ChopConfig {
		private int barCount = 14;
	}
	
	@Override
	public String toString() {
		return "Choppiness Index (CHO)";
	}

	@Override
	public ChopConfig createConfig() {
		return new ChopConfig();
	}

	@Override
	public Indicator<Num> createIndicator(ChopConfig config, BarSeries barSeries) {
		return new ChopIndicator(barSeries, config.getBarCount(), 100);
	}
	
}
