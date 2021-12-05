package com.stox.module.indicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarValue;
import com.stox.module.indicator.SimpleMovingAverage.Config;

import lombok.Data;

public class SimpleMovingAverage implements Indicator<Config, Double> {

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
		if (config.getSpan() <= values.size() || config.getSpan() <= bars.size()) {
			double sum = 0;
			for (int index = 0; index < config.getSpan(); index++) {
				sum += getValue(index, config.getBarValue(), values, bars);
			}
			return sum / config.getSpan();
		}
		return null;
	}

	@Override
	public List<Double> computeAll(List<Double> values, List<Bar> bars, Config config) {
		if (config.getSpan() <= values.size() || config.getSpan() <= bars.size()) {
			double sum = 0;
			final int size = Math.max(values.size(), bars.size());
			final List<Double> results = new ArrayList<>(size);
			for (int index = size - 1; index >= 0; index--) {
				final Double value = getValue(index, config.getBarValue(), values, bars);
				if (null == value) {
					results.add(null);
				} else {
					sum += value;
					if (index < size - config.getSpan()) {
						final Double pastValue = getValue(index + config.getSpan(), config.getBarValue(), values, bars);
						if (null != pastValue) {
							sum -= pastValue;
							results.add(sum / config.getSpan());
						}
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
