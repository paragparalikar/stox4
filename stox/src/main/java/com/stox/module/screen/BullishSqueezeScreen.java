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

	// Normalized std dev < 1
	private final StandardDeviation standardDeviation = Indicator.ofType(StandardDeviation.class);
	
	@Data
	public static class Config {
		private int span = 14;
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
		return config.getSpan();
	}

	@Override
	public boolean match(List<Bar> bars, Config config) {
		final StandardDeviation.Config standardDeviationConfig  = standardDeviation.defaultConfig();
		standardDeviationConfig.setBarValue(BarValue.CLOSE);
		standardDeviationConfig.setNormalize(true);
		standardDeviationConfig.setSpan(config.getSpan());
		final Double standardDeviationValue = standardDeviation.compute(Collections.emptyList(), bars, standardDeviationConfig);
		return null != standardDeviationValue && standardDeviationValue <= 1;
	}
	
}
