package com.stox.module.indicator;

import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarValue;
import com.stox.module.indicator.RelativeVolatility.Config;

import lombok.Data;

public class RelativeVolatility implements Indicator<Config, Double> {
	
	private final StandardDeviation stdDev = new StandardDeviation();
	private final RelativeStrengthIndex rsi = new RelativeStrengthIndex();

	@Data
	public static class Config {
		private int span = 14;
		private BarValue barValue = BarValue.CLOSE;
	}

	@Override
	public Config defaultConfig() {
		return new Config();
	}

	@Override
	public Double compute(List<Double> values, List<Bar> bars, Config config) {
		
		final StandardDeviation.Config stdDevConfig = stdDev.defaultConfig();
		stdDevConfig.setSpan(config.getSpan());
		stdDevConfig.setBarValue(config.getBarValue());
		final List<Double> stdDevValues = stdDev.computeAll(values, bars, stdDevConfig);
		
		final RelativeStrengthIndex.Config rsiConfig = rsi.defaultConfig();
		rsiConfig.setSpan(config.getSpan());
		rsiConfig.setBarValue(config.getBarValue());
		final Double result = rsi.compute(stdDevValues, bars, rsiConfig);
		
		return result;
	}

	@Override
	public List<Double> computeAll(List<Double> values, List<Bar> bars, Config config) {
		
		final StandardDeviation.Config stdDevConfig = stdDev.defaultConfig();
		stdDevConfig.setSpan(config.getSpan());
		stdDevConfig.setBarValue(config.getBarValue());
		final List<Double> stdDevValues = stdDev.computeAll(values, bars, stdDevConfig);
		
		final RelativeStrengthIndex.Config rsiConfig = rsi.defaultConfig();
		rsiConfig.setSpan(config.getSpan());
		rsiConfig.setBarValue(config.getBarValue());
		final List<Double> results = rsi.computeAll(stdDevValues, bars, rsiConfig);
		
		return results;
	}
	
}
