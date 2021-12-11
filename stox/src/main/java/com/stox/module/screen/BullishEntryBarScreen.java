package com.stox.module.screen;

import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.screen.BullishEntryBarScreen.Config;

import lombok.Getter;
import lombok.Setter;

public class BullishEntryBarScreen implements Screen<Config> {

	@Getter
	@Setter
	public static class Config {
		private int turnoverAverageSpan = 14;
		private long minimumAverageTurnover = 50_00_000;
	}
	
	@Override
	public String code() {
		return "screen-bullish-bar-entry";
	}

	@Override
	public String name() {
		return "Bullish Entry Bar";
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
		return 3 + config.getTurnoverAverageSpan();
	}

	@Override
	public boolean match(List<Bar> bars, Config config) {
		final Bar bar = bars.get(0);
		final Bar one = bars.get(1);
		final Bar two = bars.get(2);
		
		// Current bar must close at or above previous high 
		if(bar.close() < one.high()){
			return false;			
		}
		
		// ########################### Below criteria is to reduce false positives ###########################
		
		// --------------------------- Eliminate illiquid stocks ---------------------------
		
		// Current bar's open and close can not be same
		if(bar.close() == bar.open()){
			return false;
		}
		
		// Previous bar's open and close can not be same 
		if(one.open() == one.close()){
			return false;
		}
		
		// Average turnover 
		long turnoverSum = 0;
		for(int index = 0; index < config.getTurnoverAverageSpan(); index++) {
			final Bar b = bars.get(index);
			turnoverSum += b.volume() * b.close();
		}
		final long averageTurnover = turnoverSum / config.getTurnoverAverageSpan();
		if(averageTurnover < config.getMinimumAverageTurnover()) {
			return false;
		}
		
		
		// --------------------------- Eliminate overbought or high risk/reward ratio stocks ---------------------------
		
		// Previous bar must close at or below high of previous to previous bar
		if(one.close() > two.high()){
			return false;
		}
		
		return true;						
	}

}
