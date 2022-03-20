package com.stox.ml.feature;

import java.util.ArrayList;
import java.util.List;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.num.Num;

import com.stox.common.util.Maths;
import com.stox.indicator.CandleBodyIndicator;
import com.stox.indicator.PriceSpreadIndicator;
import com.stox.indicator.VolatilityContractionIndicator;

public class VolatilityContractionFeatureExtractor implements BarSeriesFeatureExtractor {
	
	@Override
	public List<Double> extract(int index, int barCount, BarSeries barSeries) {
		if(index < 2 * barCount + 1 || index >= barSeries.getBarCount()) return null;
		final List<Double> features = new ArrayList<>();
		
		final Indicator<Num> volumeIndicator = new VolumeIndicator(barSeries);
		final Indicator<Num> bodyIndicator = new CandleBodyIndicator(barSeries);
		final Indicator<Num> spreadIndicator = new PriceSpreadIndicator(barSeries);
		final Indicator<Num> averageVolumeIndicator = new SMAIndicator(volumeIndicator, 5);
		final Indicator<Num> averageBodyIndicator = new SMAIndicator(bodyIndicator, 5);
		final Indicator<Num> averageSpreadIndicator = new SMAIndicator(spreadIndicator, 5);
		
		features.add(volumeIndicator.getValue(index).dividedBy(volumeIndicator.getValue(index - 1)).doubleValue());
		features.add(bodyIndicator.getValue(index).dividedBy(bodyIndicator.getValue(index - 1)).doubleValue());
		features.add(spreadIndicator.getValue(index).dividedBy(spreadIndicator.getValue(index - 1)).doubleValue());
		features.add(volumeIndicator.getValue(index).dividedBy(averageVolumeIndicator.getValue(index - 1)).doubleValue());
		features.add(bodyIndicator.getValue(index).dividedBy(averageBodyIndicator.getValue(index - 1)).doubleValue());
		features.add(spreadIndicator.getValue(index).dividedBy(averageSpreadIndicator.getValue(index - 1)).doubleValue());
		
		double sum = 0;
		for(int fib : Maths.fib(5, barCount)) {
			final Indicator<Num> volatilityContractionIndicator = new VolatilityContractionIndicator(barSeries, fib);
			final double value = volatilityContractionIndicator.getValue(index - 1).doubleValue();
			features.add(value);
			sum += value;
		}
		features.add(sum);
		
		return features;
	}
	
}
