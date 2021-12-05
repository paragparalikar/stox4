package com.stox.module.indicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarValue;
import com.stox.module.indicator.RelativeStrengthIndex.Config;

import lombok.Data;

public class RelativeStrengthIndex implements Indicator<Config, Double> {

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
		double gainSum = 0;
		double lossSum = 0;
		final int span = config.getSpan();
		final BarValue barValue = config.getBarValue();
		for (int i = 0; i < span; i++) {
			final double delta = getValue(i, barValue, values, bars) - getValue(i + 1, barValue, values, bars);
			if (0 < delta) {
				gainSum += delta;
			} else if (0 > delta) {
				lossSum -= delta;
			}
		}
		return 100 - (100 / (1 + (gainSum / lossSum)));
	}

	@Override
	public List<Double> computeAll(List<Double> values, List<Bar> bars, Config config) {
		final int span = config.getSpan();
		final BarValue barValue = config.getBarValue();
		final int size = Math.max(values.size(), bars.size());
		final List<Double> results = new ArrayList<>(size);
		for (int index = size - 1; index >= 0; index--) {
			if (index < size - span - 1) {
				double gainSum = 0;
				double lossSum = 0;
				if (index < size - span) {
					for (int i = index; i < index + span; i++) {
						final Double value = getValue(i, barValue, values, bars);
						final Double value1 = getValue(i + 1, barValue, values, bars);
						if (null != value && null != value1) {
							final double delta = value - value1;
							gainSum += Math.max(0, delta);
							lossSum += Math.max(0, -delta);
						}
					}
				}
				final double sum = gainSum + lossSum;
				results.add(0 == sum ? null : (100 * gainSum / sum));
			} else {
				results.add(null);
			}
		}
		Collections.reverse(results);
		return results;
	}

}
