package com.stox.ml.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.ml.indicator.BuyTradeClassIndicatorProvider.BuyTradeClassConfig;

import lombok.Data;

public class BuyTradeClassIndicatorProvider implements IndicatorProvider<Integer, BuyTradeClassConfig>{

	@Data
	public static class BuyTradeClassConfig {
		private int barCount = 34;
		private int maxClass = 3;
		private double stepPercentage = 20;
	}

	@Override
	public Indicator<Integer> createIndicator(BuyTradeClassConfig config, BarSeries barSeries) {
		return new BuyTradeClassIndicator(barSeries, config.getBarCount(), config.getMaxClass(), config.getStepPercentage());
	}
	
}
