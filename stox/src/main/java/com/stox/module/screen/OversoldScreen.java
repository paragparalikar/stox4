package com.stox.module.screen;

import java.util.Collections;
import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarValue;
import com.stox.module.indicator.Indicator;
import com.stox.module.indicator.RelativeStrengthIndex;
import com.stox.module.screen.OversoldScreen.Config;

import lombok.Data;

public class OversoldScreen implements Screen<Config> {
	
	private final RelativeStrengthIndex rsiIndicator = Indicator.ofType(RelativeStrengthIndex.class);

	@Data
	public static class Config{
		private int span = 14;
		private double maxRsiValue = 30;
		private BarValue barValue = BarValue.LOW;
	}

	@Override
	public String code() {
		return "oversold";
	}

	@Override
	public String name() {
		return "Oversold";
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
		final RelativeStrengthIndex.Config rsiConfig = rsiIndicator.defaultConfig();
		rsiConfig.setSpan(config.getSpan());
		rsiConfig.setBarValue(config.getBarValue());
		final Double rsiValue = rsiIndicator.compute(Collections.emptyList(), bars, rsiConfig);
		return null != rsiValue && rsiValue < config.getMaxRsiValue();
	}

}
