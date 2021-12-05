package com.stox.module.indicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarValue;
import com.stox.module.indicator.BollingerBands.Config;
import com.stox.module.indicator.model.Channel;

import lombok.Getter;
import lombok.Setter;

public class BollingerBands implements Indicator<Config, Channel>{

	@Getter
	@Setter
	public static class Config{
		private int span = 14;
		private double multiple = 2;
		private BarValue barValue = BarValue.CLOSE;
	}
	
	@Override
	public Config defaultConfig() {
		return new Config();
	}

	private Channel compute(int index, int span, BarValue barValue, List<Double> values, List<Bar> bars, Config config) {
		double sum = 0;
		for (int i = index; i < index + span; i++) {
			sum += getValue(i, barValue, values, bars);
		}
		double deviationSum = 0;
		final double average = sum / span;
		for (int i = index; i < index + span; i++) {
			double deviation = average - getValue(i, barValue, values, bars);
			deviationSum += Math.pow(deviation, 2);
		}
		final double stdDev = Math.sqrt(deviationSum / span);
		return new Channel(average + config.getMultiple()*stdDev, average, average - config.getMultiple()*stdDev);
	}
	
	@Override
	public Channel compute(List<Double> values, List<Bar> bars, Config config) {
		if (config.getSpan() <= values.size() || config.getSpan() <= bars.size()) {
			return compute(0, config.getSpan(), config.getBarValue(), values, bars, config);
		}
		return null;
	}

	@Override
	public List<Channel> computeAll(List<Double> values, List<Bar> bars, Config config) {
		if (config.getSpan() < values.size() || config.getSpan() < bars.size()) {
			final int size = Math.max(values.size(), bars.size());
			final List<Channel> results = new ArrayList<>(size);
			for (int index = size - 1; index >= 0; index--) {
				final Double value = getValue(index, config.getBarValue(), values, bars);
				if (null == value) {
					results.add(null);
				} else {
					if (index < size - config.getSpan()) {
						results.add(compute(index, config.getSpan(), config.getBarValue(), values, bars, config));
					} else {
						results.add(null);
					}
				}
			}
			Collections.reverse(results);
			return results;
		}
		return Collections.emptyList();
	}
	
	
}
