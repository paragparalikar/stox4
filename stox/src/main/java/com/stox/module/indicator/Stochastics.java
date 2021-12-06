package com.stox.module.indicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarValue;
import com.stox.module.indicator.Stochastics.Config;
import com.stox.module.indicator.model.MultiValue;
import com.stox.util.MathUtil;

import lombok.Data;

public class Stochastics implements Indicator<Config, MultiValue> {

	@Data
	public static class Config {
		private int kSpan = 14;
		private int kSmoothing = 3;
		private int dSpan = 3;
		private BarValue barValue = BarValue.CLOSE;
	}

	@Override
	public Config defaultConfig() {
		return new Config();
	}
	
	@Override
	public MultiValue compute(List<Double> values, List<Bar> bars, Config config) {
		final int size = Math.max(values.size(), bars.size());
		final int minSize = config.getKSpan() + config.getKSmoothing() + config.getDSpan();
		if(size >= minSize) {
			return computeAll(
					values.subList(0, MathUtil.limit(0, minSize, values.size())), 
					bars.subList(0, MathUtil.limit(0, minSize, bars.size())), 
					config)
				.get(0);
		}
		return null;
	}

	@Override
	public List<MultiValue> computeAll(List<Double> values, List<Bar> bars, Config config) {
		final int size = Math.max(values.size(), bars.size());
		final int minSize = config.getKSpan() + config.getKSmoothing() + config.getDSpan();
		if(size >= minSize) {
			double kSum = 0;
			double kSmoothSum = 0;
			final List<MultiValue> results = new ArrayList<>(size - minSize);
			final List<Double> kValues = new ArrayList<>(size - config.getKSpan());
			final List<Double> kSmoothValues = new ArrayList<>(size - (config.getKSpan() + config.getKSmoothing()));
			for(int index = size - 1; index >= 0; index--) {
				MultiValue result = null;
				if(index <= size - config.getKSpan()) {
					final double k = compute(index, values, bars, config);
					kValues.add(k);
					kSum += k;
				}
				
				double kSmooth = 0;
				if(kValues.size() >= config.getKSmoothing()) {
					if(kValues.size() > config.getKSmoothing()) {
						final double pastKValue = kValues.get(kValues.size() - (config.getKSmoothing() + 1));
						kSum -= pastKValue;
					}
					kSmooth = kSum / config.getKSmoothing();
					kSmoothValues.add(kSmooth);
					kSmoothSum += kSmooth;
				}
				
				if(kSmoothValues.size() >= config.getDSpan()) {
					if(kSmoothValues.size() > config.getDSpan()) {
						final double pastKSmooth = kSmoothValues.get(kSmoothValues.size() - (config.getDSpan() + 1));
						kSmoothSum -= pastKSmooth;
					}
					final double dValue = kSmoothSum / config.getDSpan();
					result = new MultiValue(new double[] {kSmooth, dValue});
				}
				
				results.add(result);
			}
			Collections.reverse(results);
			return results;
		}
		return Collections.emptyList();
	}
	
	private Double compute(int index, List<Double> values, List<Bar> bars, Config config) {
		double high = Double.MIN_VALUE;
		double low = Double.MAX_VALUE;
		for(int j = config.getKSpan() - 1; j >= 0; j--) {
			final Double value = getValue(index + j, config.getBarValue(), values, bars);
			low = Math.min(low, value);
			high = Math.max(high, value);
		}
		final Double value = getValue(index, config.getBarValue(), values, bars);
		final double k = 100 * ((value - low) / (high - low));
		return k;
	}
	
}
