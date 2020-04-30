package com.stox.module.indicator;

import java.util.List;
import java.util.stream.Collectors;

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

	private Double value(final Bar bar) {
		return Math.log(
				BarValue.SPREAD.resolve(bar) * BarValue.BODY.resolve(bar) * Math.abs(bar.close() - BarValue.MID.resolve(bar)));
	}

	@Override
	public Double compute(List<Double> values, List<Bar> bars, Config config) {
		return null != bars && !bars.isEmpty() ? value(bars.get(0)) : null;
	}

	@Override
	public List<Double> computeAll(List<Double> values, List<Bar> bars, Config config) {
		return bars.stream().map(bar -> value(bar)).collect(Collectors.toList());
	}

}
