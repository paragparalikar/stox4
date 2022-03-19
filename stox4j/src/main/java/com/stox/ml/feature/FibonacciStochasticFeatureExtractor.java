package com.stox.ml.feature;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.num.Num;

import com.stox.common.util.Maths;
import com.stox.indicator.StochasticOccilatorIndicator;

import lombok.NonNull;
import lombok.Setter;

public class FibonacciStochasticFeatureExtractor implements BarSeriesFeatureExtractor {

	@Setter @NonNull private Function<BarSeries, Indicator<Num>> indicatorFunction;
	
	@Override
	public List<Double> extract(int index, int barCount, BarSeries barSeries) {
		final List<Double> features = new LinkedList<>();
		final List<Integer> fibBarCounts = Maths.fib(2, barCount);
		if(!fibBarCounts.contains(barCount)) fibBarCounts.add(barCount);
		if(index < barCount || index >= barSeries.getBarCount()) {
			fibBarCounts.forEach(fib -> features.add(null));
			return features;
		}
		final Indicator<Num> indicator = indicatorFunction.apply(barSeries);
		for(int fibBarCountIndex = 0; fibBarCountIndex < fibBarCounts.size(); fibBarCountIndex++) {
			final int fibBarCount = fibBarCounts.get(fibBarCountIndex);
			final StochasticOccilatorIndicator stochasticIndicator = new StochasticOccilatorIndicator(indicator, fibBarCount);
			final Num feature = stochasticIndicator.getValue(index);
			features.add(null == feature || feature.isNaN() ? 0 : feature.doubleValue());
		}
		return features;
	}

}
