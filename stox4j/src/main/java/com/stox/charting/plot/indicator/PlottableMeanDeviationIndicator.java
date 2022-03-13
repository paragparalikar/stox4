package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.statistics.MeanDeviationIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.indicator.PlottableMeanDeviationIndicator.MeanDeviationConfig;
import com.stox.common.bar.BarValueType;

import lombok.Data;

public class PlottableMeanDeviationIndicator implements PlottableLineIndicator<MeanDeviationConfig> {

	@Data
	public static class MeanDeviationConfig {
		private int barCount = 14;
		private BarValueType barValueType = BarValueType.CLOSE;
	}
	
	@Override
	public String toString() {
		return "Mean Deviation (MDI)";
	}

	@Override
	public MeanDeviationConfig createConfig() {
		return new MeanDeviationConfig();
	}

	@Override
	public Indicator<Num> createIndicator(MeanDeviationConfig config, BarSeries barSeries) {
		return new MeanDeviationIndicator(createIndicator(config.getBarValueType(), barSeries), config.getBarCount());
	}
	
}