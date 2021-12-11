package com.stox.module.indicator;

import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.Move;
import com.stox.module.indicator.ZigZagIndicator.Config;

import lombok.Getter;
import lombok.Setter;

public class ZigZagIndicator implements Indicator<Config, Move> {

	@Getter
	@Setter
	public static class Config {
		private double tolarancePercentage = 5;
	}

	@Override
	public Config defaultConfig() {
		return new Config();
	}

	@Override
	public Move compute(List<Double> values, List<Bar> bars, Config config) {
		throw new UnsupportedOperationException();
	}

	@Override
	public List<Move> computeAll(List<Double> values, List<Bar> bars, Config config) {
		return Move.parse(bars, config.getTolarancePercentage());
	}

}
