package com.stox.charting.plot.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.candles.DojiIndicator;

import com.stox.charting.plot.rule.PlottableDojiRule.DojiConfig;

import lombok.Data;

public class PlottableDojiRule extends AbstractPlottableRule<DojiConfig> {

	@Data
	public static class DojiConfig {
		private int barCount = 8;
		private double bodyFactor = 0.2;
	}
	
	@Override
	public String toString() {
		return "Candlestick Pattern - Doji";
	}

	@Override
	public DojiConfig createConfig() {
		return new DojiConfig();
	}

	@Override
	public Indicator<Boolean> createIndicator(DojiConfig config, BarSeries barSeries) {
		return new DojiIndicator(barSeries, config.getBarCount(), config.getBodyFactor());
	}
	
}
