package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.indicator.PlottableVolatilityIndicator.VolatilityConfig;
import com.stox.indicator.VolatilityIndicator;

import lombok.Data;

public class PlottableVolatilityIndicator implements PlottableLineIndicator<VolatilityConfig> {
	
	@Data
	public static class VolatilityConfig {
		private int barCount = 34;
		public String toString() {return String.format("BarCount: %d", barCount);}
	}

	@Override
	public VolatilityConfig createConfig() {
		return new VolatilityConfig();
	}

	@Override
	public Indicator<Num> createIndicator(VolatilityConfig config, BarSeries barSeries) {
		return new VolatilityIndicator(barSeries, config.getBarCount());
	}

}
