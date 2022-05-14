package com.stox.charting.plot.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.statistics.SigmaIndicator;
import org.ta4j.core.num.Num;

import com.stox.charting.plot.indicator.PlottableSigmaIndicator.SigmaConfig;
import com.stox.common.bar.BarValueType;

import lombok.Data;

public class PlottableSigmaIndicator implements PlottableLineIndicator<SigmaConfig> {

	@Data
	public static class SigmaConfig {
		private int barCount = 14;
		private BarValueType barValueType = BarValueType.CLOSE;
		public String toString() {return String.format("BarCount: %d, Type: %s", barCount, barValueType.name());}
	}
	
	@Override
	public String toString() {
		return "Z-score (ZSI)";
	}

	@Override
	public SigmaConfig createConfig() {
		return new SigmaConfig();
	}

	@Override
	public Indicator<Num> createIndicator(SigmaConfig config, BarSeries barSeries) {
		final Indicator<Num> indicator = config.getBarValueType().createIndicator(barSeries);
		return new SigmaIndicator(indicator, config.getBarCount());
	}
	
}
