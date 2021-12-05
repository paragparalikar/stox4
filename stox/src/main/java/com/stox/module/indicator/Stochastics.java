package com.stox.module.indicator;

import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarValue;
import com.stox.module.indicator.Stochastics.Config;
import com.stox.module.indicator.Stochastics.Value;

import lombok.Data;

public class Stochastics implements Indicator<Config, Value> {

	@Data
	public static class Config {
		private int kSpan = 14;
		private int kSmoothing = 3;
		private int dSpan = 3;
		private BarValue barValue = BarValue.CLOSE;
	}
	
	@Data
	public static class Value {
		private double k, v;
	}

	@Override
	public Config defaultConfig() {
		return new Config();
	}

	@Override
	public Value compute(List<Double> values, List<Bar> bars, Config config) {
		if(values.size() >= config.getKSpan() || bars.size() >= config.getKSpan()) {
			
		}
		return null;
	}

	@Override
	public List<Value> computeAll(List<Double> values, List<Bar> bars, Config config) {
		return null;
	}
	
}
