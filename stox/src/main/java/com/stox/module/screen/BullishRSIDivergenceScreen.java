package com.stox.module.screen;

import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.screen.BullishRSIDivergenceScreen.Config;

import lombok.Data;

public class BullishRSIDivergenceScreen implements Screen<Config> {

	@Data
	public static class Config {
		
	}

	@Override
	public String code() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String name() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ScreenType screenType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Config defaultConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int minBarCount(Config config) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean match(List<Bar> bars, Config config) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
