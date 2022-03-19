package com.stox.ml.feature;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.num.Num;

import com.stox.common.util.Maths;

import lombok.NonNull;
import lombok.Setter;

public class RatioWithSmaFeatureExtractor implements BarSeriesFeatureExtractor {

	@Setter @NonNull private Function<BarSeries, Indicator<Num>> indicatorFunction;
	
	@Override
	public List<Double> extract(int index, int barCount, BarSeries barSeries) {
		final List<Double> features = new LinkedList<>();
		final List<Integer> fibBarCounts = Maths.fib(1, barCount);
		if(!fibBarCounts.contains(barCount)) fibBarCounts.add(barCount);
		
		if(index < barCount || index >= barSeries.getBarCount()) {
			fibBarCounts.forEach(fib -> features.add(null));
			return features;
		}
		
		final Indicator<Num> indicator = indicatorFunction.apply(barSeries);
		final Num value = indicator.getValue(index);
		
		if(null == value) {
			fibBarCounts.forEach(fib -> features.add(null));
			return features;
		}
		
		for(int fibBarCountIndex = 0; fibBarCountIndex < fibBarCounts.size(); fibBarCountIndex++) {
			final int fibBarCount = fibBarCounts.get(fibBarCountIndex);
			final Indicator<Num> smaIndicator = new SMAIndicator(indicator, fibBarCount);
			final Num averageValue = smaIndicator.getValue(index);
			final Double feature = null == averageValue || averageValue.isNaN() ? 0 : 
				value.dividedBy(averageValue).doubleValue();
			features.add(feature.isNaN() || feature.isInfinite() ? 0 : feature);
		}
		return features;
	}

}
