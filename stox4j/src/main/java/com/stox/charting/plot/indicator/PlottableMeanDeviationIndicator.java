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
		public String toString() {return String.format("BarCount: %d, Type: %s", barCount, barValueType.name());}
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
		final Indicator<Num> indicator = config.getBarValueType().createIndicator(barSeries);
		return new MeanDeviationIndicator(indicator, config.getBarCount());
	}
	
}
