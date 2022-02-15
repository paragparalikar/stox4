package com.stox.module.screen;

import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.screen.BullishBarScreen.Config;

import lombok.Getter;
import lombok.Setter;

public class BullishBarScreen implements Screen<Config> {

	@Getter
	@Setter
	public static class Config {
	}
	
	@Override
	public String code() {
		return "screen-bullish-bar";
	}

	@Override
	public String name() {
		return "Bullish Bar";
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
		return 3;
	}

	@Override
	public boolean match(List<Bar> bars, Config config) {
		final Bar bar = bars.get(0);
		final Bar one = bars.get(1);
		final Bar two = bars.get(2);
		
		if(bar.close() < one.high()) return false;			
		if(one.close() > two.high()) return false;
		
		return true;						
	}

}
