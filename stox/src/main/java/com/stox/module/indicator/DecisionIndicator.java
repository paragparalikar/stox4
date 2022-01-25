package com.stox.module.indicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarValue;
import com.stox.module.indicator.DecisionIndicator.Config;

public class DecisionIndicator implements Indicator<Config, Double> {

	public static class Config {

	}

	@Override
	public Config defaultConfig() {
		return new Config();
	}

	@Override
	public Double compute(List<Double> values, List<Bar> bars, Config config) {
		if(null != bars && 2 <= bars.size()) {
			final Bar bar = bars.get(0);
			final double mid = BarValue.MID.resolve(bar);
			final double spread = BarValue.SPREAD.resolve(bar);
			final double body = BarValue.BODY.resolve(bar);
			final double closeDelta = Math.abs(bar.close() - BarValue.MID.resolve(bar));
			final double closeChange = Math.abs(bar.close() - bars.get(1).close());
			final double avg = (spread + body + closeDelta + closeChange) / 4;
			return avg * 100 / mid;
		}
		return null;
	}

	@Override
	public List<Double> computeAll(List<Double> values, List<Bar> bars, Config config) {
		if(null != bars && !bars.isEmpty()) {
			if(2 <= bars.size()) {
				final List<Double> results = new ArrayList<>(bars.size());
				for(int index = 0; index < bars.size() - 1; index++) {
					final List<Bar> input = bars.subList(index, index + 2);
					results.add(compute(values, input, config));
				}
				results.add(null);
				return results;
			}
			return Collections.singletonList(null);
		}
		return Collections.emptyList();
	}

}
