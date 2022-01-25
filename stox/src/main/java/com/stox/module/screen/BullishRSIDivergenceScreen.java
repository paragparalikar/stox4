package com.stox.module.screen;

import java.util.Collections;
import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarValue;
import com.stox.module.indicator.Indicator;
import com.stox.module.indicator.RelativeStrengthIndex;
import com.stox.module.screen.BullishRSIDivergenceScreen.Config;

import lombok.Data;

public class BullishRSIDivergenceScreen implements Screen<Config> {
	
	private RelativeStrengthIndex rsi = Indicator.ofType(RelativeStrengthIndex.class);

	@Data
	public static class Config {
		private int span = 8;
		private double maxOversoldRSI = 40;
		private BarValue barValue = BarValue.CLOSE;
	}

	@Override
	public String code() {
		return "rsidiv";
	}

	@Override
	public String name() {
		return "RSI Divergence";
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
		return config.getSpan() * 3;
	}

	@Override
	public boolean match(List<Bar> bars, Config config) {
		final Bar bar = bars.get(0);
		final BarValue barValue = config.getBarValue();
		final Bar pastBar = bars.get(config.getSpan());
		if(barValue.resolve(bar) > barValue.resolve(pastBar)) {
			return false;
		}
		
		final RelativeStrengthIndex.Config rsiConfig = rsi.defaultConfig();
		rsiConfig.setSpan(config.getSpan());
		rsiConfig.setBarValue(barValue);
		
		final List<Bar> pastBars = bars.subList(config.getSpan(), bars.size());
		final double pastRSIValue = rsi.compute(Collections.emptyList(), pastBars, rsiConfig);
		
		if(config.getMaxOversoldRSI() < pastRSIValue) {
			return false;
		}
		
		final double currentRSIValue = rsi.compute(Collections.emptyList(), bars, rsiConfig);
		
		if(pastRSIValue > currentRSIValue) {
			return false;
		}
		
		return true;
	}
	
	
}
