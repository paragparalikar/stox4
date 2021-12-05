package com.stox.module.indicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarValue;
import com.stox.module.indicator.ExponentialMovingAverage.Config;

import lombok.Data;

public class ExponentialMovingAverage implements Indicator<Config, Double> {

	private final SimpleMovingAverage sma = new SimpleMovingAverage();
	
	@Data
	public static class Config {
		private int span = 100;
		private BarValue barValue = BarValue.CLOSE;
	}

	@Override
	public Config defaultConfig() {
		return new Config();
	}

	@Override
	public Double compute(List<Double> values, List<Bar> bars, Config config) {
		if (config.getSpan() <= values.size() || config.getSpan() <= bars.size()) {
			final SimpleMovingAverage.Config smaConfig = sma.defaultConfig();
			smaConfig.setSpan(config.getSpan());
			smaConfig.setBarValue(config.getBarValue());
			double ema = sma.compute(values, bars, smaConfig); // SMA is initial EMA
			final double k = 2/(config.getSpan() + 1);
			
			for (int index = config.getSpan() - 1; index >= 0; index--) {
				final Double value = getValue(index, config.getBarValue(), values, bars);
				ema = value * k + ema * (1 - k);
			}
			
			return ema;
		}
		return null;
	}

	@Override
	public List<Double> computeAll(List<Double> values, List<Bar> bars, Config config) {
		if (config.getSpan() <= values.size() || config.getSpan() <= bars.size()) {
			final SimpleMovingAverage.Config smaConfig = sma.defaultConfig();
			smaConfig.setSpan(config.getSpan());
			smaConfig.setBarValue(config.getBarValue());
			double ema = sma.compute(
					values.subList(Math.max(0, values.size() - config.getSpan()), values.size()), 
					bars.subList(Math.max(0, bars.size() - config.getSpan()), bars.size()), 
					smaConfig); // SMA is initial EMA
			final double k = 2d/(double)(config.getSpan() + 1);
			final int size = Math.max(values.size(), bars.size());
			
			final List<Double> results = new ArrayList<>(size);
			for (int index = size - 1; index >= 0; index--) {
				final Double value = getValue(index, config.getBarValue(), values, bars);
				if(null == value) {
					results.add(null);
				} else {
					ema = (value * k) + (ema * ( 1 - k));
					results.add(ema);
				}
			}
			Collections.reverse(results);
			return results;
		}
		return Collections.emptyList();
	}
	
}
