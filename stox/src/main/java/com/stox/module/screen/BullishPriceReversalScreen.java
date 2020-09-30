package com.stox.module.screen;

import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.indicator.Indicator;
import com.stox.module.indicator.RelativeStrengthIndex;
import com.stox.module.screen.BullishPriceReversalScreen.Config;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public class BullishPriceReversalScreen implements Screen<Config> {
	
	private final RelativeStrengthIndex rsi = Indicator.ofType(RelativeStrengthIndex.class);
	
	@Getter
	@Setter
	@Accessors(fluent = true)
	public static class Config{
		private int span = 14;
		private double maxRsi = 35;
	}

	@Override
	public String code() {
		return "price-reversal";
	}

	@Override
	public String name() {
		return "Price Reversal";
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
		return config.span() + 1;
	}

	@Override
	public boolean match(final List<Bar> bars,final Config config) {
		
		final Bar bar = bars.get(0);
		final Bar one = bars.get(1);
		final Bar two = bars.get(2);
		
		// Current bar must close at or above a third of the spread.
		if(bar.close() < bar.high() - (bar.high() - bar.low())/3) {
			return false;
		}
		
		//Previous bar's high, close and low must be lower than that of previous to that bar.
		if(one.close() > two.close() || one.high() > two.high() || one.low() > two.low()) {
			return false;
		}
		
		final RelativeStrengthIndex.Config rsiConfig = rsi.defaultConfig();
		rsiConfig.setSpan(config.span());
		if(config.maxRsi() < rsi.compute(null, bars.subList(1, bars.size()), rsiConfig)) {
			return false;
		}
		
		return true;
	}

}
