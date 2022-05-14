package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.ROCIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.indicator.PlottableROCIndicator.ROCConfig;
import com.stox.common.bar.BarValueType;

import lombok.Data;

public class PlottableROCIndicator implements PlottableLineIndicator<ROCConfig> {

	@Data
	public static class ROCConfig{
		private int barCount = 14;
		private BarValueType barValueType = BarValueType.CLOSE;
		public String toString() {return String.format("BarCount: %d, Type: %s", barCount, barValueType.name());}
	}
	
	@Override
	public String toString() {
		return "Rate Of Change (ROC)";
	}

	@Override
	public ROCConfig createConfig() {
		return new ROCConfig();
	}

	@Override
	public Indicator<Num> createIndicator(ROCConfig config, BarSeries barSeries) {
		final Indicator<Num> indicator = config.getBarValueType().createIndicator(barSeries);
		return new ROCIndicator(indicator, config.getBarCount());
	}
	
}
