package com.stox.module.ranker.model;

import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.ranker.model.TestRanker.Config;

import lombok.Data;

public class TestRanker implements Ranker<Config> {

	@Data
	public static class Config {
		private int span = 8;
	}

	@Override
	public double rank(List<Bar> bars, Config config) {
		return bars.get(0).close();
	}

	@Override
	public int minBarCount(Config config) {
		return 100;
	}

	@Override
	public Config defaultConfig() {
		return new Config();
	}
	
	@Override
	public String toString() {
		return "Test";
	}

}
