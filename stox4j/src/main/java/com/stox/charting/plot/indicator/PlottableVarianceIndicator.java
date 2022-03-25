package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.statistics.VarianceIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.indicator.PlottableVarianceIndicator.VarianceConfig;
import com.stox.common.bar.BarValueType;

import lombok.Data;

public class PlottableVarianceIndicator implements PlottableLineIndicator<VarianceConfig> {

	@Data
	public static class VarianceConfig {
		private int barCount = 14;
		private BarValueType barValueType = BarValueType.CLOSE;
		public String toString() {return String.format("BarCount: %d, Type: %s", barCount, barValueType.name());}
	}
	
	@Override
	public String toString() {
		return "Variance (VI)";
	}

	@Override
	public VarianceConfig createConfig() {
		return new VarianceConfig();
	}

	@Override
	public Indicator<Num> createIndicator(VarianceConfig config, BarSeries barSeries) {
		return new VarianceIndicator(createIndicator(config.getBarValueType(), barSeries), config.getBarCount());
	}
	
}
