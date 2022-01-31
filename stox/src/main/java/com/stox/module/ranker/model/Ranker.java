package com.stox.module.ranker.model;

import java.util.List;

import com.stox.module.core.model.Bar;

public interface Ranker<T> {
	
	public static final Ranker<?>[] ALL = {new TestRanker()};

	double rank(List<Bar> bars, T config);
	
	int minBarCount(T config);
	
	T defaultConfig();
	
}
