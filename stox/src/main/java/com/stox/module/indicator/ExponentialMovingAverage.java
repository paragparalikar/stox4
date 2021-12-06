package com.stox.module.indicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarValue;
import com.stox.module.indicator.ExponentialMovingAverage.Config;
import com.stox.util.MathUtil;

import lombok.Data;

public class ExponentialMovingAverage implements Indicator<Config, Double> {

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
		final int size = Math.max(values.size(), bars.size());
		if (config.getSpan() <= size) {
			return computeAll(
					values.subList(0, MathUtil.limit(0, config.getSpan(), values.size())),
					bars.subList(0, MathUtil.limit(0, config.getSpan(), bars.size())),
					config)
				.get(0);
		}
		return null;
	}
	
	@Override
	public List<Double> computeAll(List<Double> values, List<Bar> bars, Config config) {
		final int size = Math.max(values.size(), bars.size());
		if (config.getSpan() <= size) {
			double sum = 0;
			final double k = 2d/(double)(config.getSpan() + 1);
			final List<Double> smaValues = new ArrayList<>(size);
			final List<Double> emaValues = new ArrayList<>(size);
			for (int index = size - 1; index >= 0; index--) {
				final Double value = getValue(index, config.getBarValue(), values, bars);
				if (null == value) {
					smaValues.add(null);
					emaValues.add(null);
				} else {
					sum += value;
					Double result = null;
					if(index <= size - config.getSpan()) {
						if (index < size - config.getSpan()) {
							final Double pastValue = getValue(index + config.getSpan(), config.getBarValue(), values, bars);
							if (null != pastValue) sum -= pastValue;
						} 
						final double sma = sum / config.getSpan();
						smaValues.add(sma);
						double ema = sma;
						for(int emaIndex = index + config.getSpan() - 1; emaIndex >= index; emaIndex--) {
							final Double emaIndexBarValue = getValue(emaIndex, config.getBarValue(), values, bars);
							ema = (emaIndexBarValue * k) + (ema * ( 1 - k));
						}
						result = ema;
					}
					emaValues.add(result);
				}
			}
			Collections.reverse(emaValues);
			return emaValues;
		}
		return Collections.emptyList();
	}

}
