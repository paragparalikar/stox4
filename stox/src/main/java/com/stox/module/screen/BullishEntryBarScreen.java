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
		private int overboughtSpan = 5;
		private double overboughtMaxPercentageRise = 15;
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
		return 3 + config.getOverboughtSpan();
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
		
		// Current, previous and previous to previous bars must have turnover above ????
		
		
		
		// --------------------------- Eliminate overbought or high risk/reward ratio stocks ---------------------------
		
		// Previous bar must close at or below high of previous to previous bar
		if(one.close() > two.high()){
			return false;
		}
			
		// Price must not have risen above 10???? % in last n???? bars
		/*double min = Double.MAX_VALUE;
		for(int index = 0; index < config.getOverboughtSpan(); index++){
			min = Math.min(min, bars.get(index).getLow());
		}
		if((bar.getClose()-min)*100/min > config.getOverboughtMaxPercentageRise()){
			return false;
		}*/
		
		// --------------------------- Eliminate buying climaxes or too much resistance to rise ---------------------------
		
		// Current bar volume must be lower than m??? times the average volume of last n???? bars
		
		// Current bar spread must be lower than m??? times the average spread of last n???? bars
		
		return true;						
	}

}
