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
		return new ROCIndicator(createIndicator(config.getBarValueType(), barSeries), config.getBarCount());
	}
	
}
