package com.stox.module.screen;

import java.util.Collections;
import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarValue;
import com.stox.module.indicator.ExponentialMovingAverage;
import com.stox.module.indicator.Indicator;
import com.stox.module.indicator.SimpleMovingAverage;
import com.stox.module.indicator.Stochastics;
import com.stox.module.screen.BhedaScreen.Config;

import lombok.Data;

public class BhedaScreen implements Screen<Config> {
	
	private final Stochastics stoch = Indicator.ofType(Stochastics.class);
	private final SimpleMovingAverage sma = Indicator.ofType(SimpleMovingAverage.class);
	private final ExponentialMovingAverage ema = Indicator.ofType(ExponentialMovingAverage.class);

	@Data
	public static class Config {
		private long minimumAverageTurnover = 100_000_000;
		private int emaSpan = 100;
		private BarValue emaBarValue = BarValue.HIGH;
		private int stochastics1KSpan = 14;
		private int stochastics1KSmoothing = 3;
		private int stochastics1DSpan = 3;
	}

	@Override
	public String code() {
		return "bheda";
	}

	@Override
	public String name() {
		return "Bheda";
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
		int max = config.getEmaSpan();
		max = Math.max(max, config.getStochastics1DSpan() + config.getStochastics1KSmoothing() + config.getStochastics1KSpan());
		return max;
	}

	@Override
	public boolean match(List<Bar> bars, Config config) {
		final SimpleMovingAverage.Config smaConfig = sma.defaultConfig();
		smaConfig.setBarValue(BarValue.CLOSE);
		smaConfig.setSpan(config.getEmaSpan());
		final double avgClose = sma.compute(Collections.emptyList(), bars, smaConfig);
		smaConfig.setBarValue(BarValue.VOLUME);
		final double avgVolume = sma.compute(Collections.emptyList(), bars, smaConfig);
		final double avgTurnover = avgClose * avgVolume;
		
		if(avgTurnover < config.getMinimumAverageTurnover()) {
			return false;
		}
		
		final Bar bar = bars.get(0);
		final ExponentialMovingAverage.Config emaConfig = ema.defaultConfig();
		emaConfig.setBarValue(BarValue.HIGH);
		emaConfig.setSpan(config.getEmaSpan());
		final double emaValue = ema.compute(Collections.emptyList(), bars, emaConfig);
		
		if(bar.low() > emaValue || bar.close() < emaValue) {
			return false;
		}
		
		
		
		return true;
	}
	
}
