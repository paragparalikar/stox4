package com.stox.ml.feature;

import java.util.ArrayList;
import java.util.List;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.DifferenceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.OpenPriceIndicator;
import org.ta4j.core.indicators.helpers.TransformIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.num.Num;

import com.stox.common.util.Maths;
import com.stox.indicator.VolatilityContractionIndicator;

public class VolatilityContractionFeatureExtractor implements BarSeriesFeatureExtractor {
	
	@Override
	public List<Double> extract(int index, int barCount, BarSeries barSeries) {
		if(index < 2 * barCount + 1 || index >= barSeries.getBarCount()) return null;
		final List<Double> features = new ArrayList<>();
		
		final Indicator<Num> volumeIndicator = new VolumeIndicator(barSeries);
		final Indicator<Num> openPriceIndicator = new OpenPriceIndicator(barSeries);
		final Indicator<Num> closePriceIndicator = new ClosePriceIndicator(barSeries);
		final Indicator<Num> bodyIndicator = new DifferenceIndicator(closePriceIndicator, openPriceIndicator);
		final Indicator<Num> absBodyIndicator = new TransformIndicator(bodyIndicator, Num::abs);
		final Indicator<Num> highPriceIndicator = new HighPriceIndicator(barSeries);
		final Indicator<Num> lowPriceIndicator = new LowPriceIndicator(barSeries);
		final Indicator<Num> spreadIndicator = new DifferenceIndicator(highPriceIndicator, lowPriceIndicator);
		final Indicator<Num> averageVolumeIndicator = new SMAIndicator(volumeIndicator, 5);
		final Indicator<Num> averageBodyIndicator = new SMAIndicator(absBodyIndicator, 5);
		final Indicator<Num> averageSpreadIndicator = new SMAIndicator(spreadIndicator, 5);
		
		features.add(volumeIndicator.getValue(index).dividedBy(volumeIndicator.getValue(index - 1)).doubleValue());
		features.add(absBodyIndicator.getValue(index).dividedBy(absBodyIndicator.getValue(index - 1)).doubleValue());
		features.add(spreadIndicator.getValue(index).dividedBy(spreadIndicator.getValue(index - 1)).doubleValue());
		features.add(volumeIndicator.getValue(index).dividedBy(averageVolumeIndicator.getValue(index - 1)).doubleValue());
		features.add(absBodyIndicator.getValue(index).dividedBy(averageBodyIndicator.getValue(index - 1)).doubleValue());
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
