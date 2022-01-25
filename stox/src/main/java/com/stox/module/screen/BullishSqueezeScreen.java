package com.stox.module.screen;

import java.util.Collections;
import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarValue;
import com.stox.module.indicator.Indicator;
import com.stox.module.indicator.StandardDeviation;
import com.stox.module.screen.BullishSqueezeScreen.Config;

import lombok.Data;

public class BullishSqueezeScreen implements Screen<Config> {

	private final StandardDeviation standardDeviation = Indicator.ofType(StandardDeviation.class);
	
	@Data
	public static class Config {
		private int shortSpan = 30;
		private double shortMaxStdDevValue = 3;
		private int longSpan = 100;
		private double longMaxStdDevValue = 15;
	}

	@Override
	public String code() {
		return "squeeze";
	}

	@Override
	public String name() {
		return "Squeeze";
	}

	@Override
	public ScreenType screenType() {
		return ScreenType.BULLISH;
	}

	@Override
	public Config defaultConfig() {
		return new Config();
	}

	@Override
	public int minBarCount(Config config) {
		return Math.max(config.getLongSpan(), config.getShortSpan());
	}

	@Override
	public boolean match(List<Bar> bars, Config config) {
		final StandardDeviation.Config standardDeviationConfig  = standardDeviation.defaultConfig();
		standardDeviationConfig.setBarValue(BarValue.CLOSE);
		standardDeviationConfig.setNormalize(true);
		standardDeviationConfig.setSpan(config.getShortSpan());
		final Double shortStandardDeviationValue = standardDeviation.compute(Collections.emptyList(), bars, standardDeviationConfig);
		if (null == shortStandardDeviationValue || shortStandardDeviationValue > config.getShortMaxStdDevValue()) {
			return false;
		}
		
		standardDeviationConfig.setSpan(config.getLongSpan());
		final Double longStandardDeviationValue = standardDeviation.compute(Collections.emptyList(), bars, standardDeviationConfig);
		if (null == longStandardDeviationValue || longStandardDeviationValue > config.getLongMaxStdDevValue()) {
			return false;
		}
		
		return true;
	}
	
}
