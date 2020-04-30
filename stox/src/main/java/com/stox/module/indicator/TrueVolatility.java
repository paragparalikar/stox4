package com.stox.module.indicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.indicator.TrueVolatility.Config;

import lombok.Getter;
import lombok.Setter;

public class TrueVolatility implements Indicator<Config, Double> {

	@Getter
	@Setter
	public static class Config {
		private int span = 14;
	}

	@Override
	public Config defaultConfig() {
		return new Config();
	}

	@Override
	public Double compute(List<Double> values, List<Bar> bars, Config config) {
		return null;
	}

	private Double getValue(final int index, List<Double> values, List<Bar> bars) {
		return values.isEmpty() ? bars.get(index).close() : values.get(index);
	}

	@Override
	public List<Double> computeAll(List<Double> values, List<Bar> bars, Config config) {
		if (config.getSpan() <= values.size() || config.getSpan() <= bars.size()) {
			final int size = Math.max(values.size(), bars.size());
			final List<Double> results = new ArrayList<>(size);
			for (int index = size - 1; index >= 0; index--) {
				final Double value = getValue(index, values, bars);
				if (null == value) {
					results.add(null);
				} else {
					if (index < size - config.getSpan()) {
						double sum = 0;
						for (int i = index + config.getSpan(); i >= index; i--) {
							sum += getValue(i, values, bars);
						}
						final double average = sum/config.getSpan();
						double deltaSum = 0;
						for (int i = index + config.getSpan(); i >= index; i--) {
							deltaSum += Math.abs(value - average);
						}
						results.add(deltaSum);
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
