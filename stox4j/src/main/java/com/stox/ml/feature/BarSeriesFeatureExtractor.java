package com.stox.ml.feature;

import java.util.List;

import org.ta4j.core.BarSeries;

public interface BarSeriesFeatureExtractor {
	
	public List<Double> extract(int index, int barCount, BarSeries barSeries);
	
}
