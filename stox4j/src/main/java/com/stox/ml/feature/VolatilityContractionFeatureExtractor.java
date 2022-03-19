package com.stox.ml.feature;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.TRIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;

import com.stox.indicator.CandleBodyIndicator;
import com.stox.indicator.PriceSpreadIndicator;

public class VolatilityContractionFeatureExtractor implements BarSeriesFeatureExtractor {

	private final Function<BarSeries, Indicator<Num>> closePriceIndicatorFunction = ClosePriceIndicator::new;	
	private final Function<BarSeries, Indicator<Num>> highPriceIndicatorFunction = HighPriceIndicator::new;
	private final Function<BarSeries, Indicator<Num>> volumeIndicatorFunction = VolumeIndicator::new;
	private final Function<BarSeries, Indicator<Num>> priceSpreadIndicatorFunction = PriceSpreadIndicator::new;
	private final Function<BarSeries, Indicator<Num>> candleBodyIndicatorFunction = CandleBodyIndicator::new;
	private final Function<BarSeries, Indicator<Num>> trueRangeIndicatorFunction = TRIndicator::new;
	private final Function<BarSeries, Indicator<Num>> avgVolumeIndicatorFunction = getAverageIndicatorFunction(volumeIndicatorFunction);
	private final Function<BarSeries, Indicator<Num>> avgPriceSpreadIndicatorFunction = getAverageIndicatorFunction(priceSpreadIndicatorFunction);
	private final Function<BarSeries, Indicator<Num>> avgCandleBodyIndicatorFunction = getAverageIndicatorFunction(candleBodyIndicatorFunction);
	private final Function<BarSeries, Indicator<Num>> avgTrueRangeIndicatorFunction = getAverageIndicatorFunction(trueRangeIndicatorFunction);
	private final RatioWithSmaFeatureExtractor ratioWithSmaFeatureExtractor = new RatioWithSmaFeatureExtractor();
	private final FibonacciStochasticFeatureExtractor fibonacciStochasticFeatureExtractor = new FibonacciStochasticFeatureExtractor();
	
	@Override
	public List<Double> extract(int index, int barCount, BarSeries barSeries) {
		if(index < 2 * barCount + 1 || index >= barSeries.getBarCount()) return null;
		final List<Double> features = new ArrayList<>();
		extract(index, barCount, barSeries, features);
		extract(index - 1, barCount, barSeries, features);
		return features;
	}
	
	private void extract(int index, int barCount, BarSeries barSeries, List<Double> features) {
		final Function<BarSeries, Indicator<Num>> standardDeviationIndicatorFunction = 
				series -> new StandardDeviationIndicator(new ClosePriceIndicator(barSeries), barCount);
		
		features.addAll(extractStochFeatures(index, barCount, barSeries, closePriceIndicatorFunction));
		features.addAll(extractStochFeatures(index, barCount, barSeries, highPriceIndicatorFunction));
		features.addAll(extractStochFeatures(index, barCount, barSeries, volumeIndicatorFunction));
		features.addAll(extractStochFeatures(index, barCount, barSeries, priceSpreadIndicatorFunction));
		features.addAll(extractStochFeatures(index, barCount, barSeries, candleBodyIndicatorFunction));
		features.addAll(extractStochFeatures(index, barCount, barSeries, trueRangeIndicatorFunction));
		features.addAll(extractStochFeatures(index, barCount, barSeries, avgVolumeIndicatorFunction));
		features.addAll(extractStochFeatures(index, barCount, barSeries, avgPriceSpreadIndicatorFunction));
		features.addAll(extractStochFeatures(index, barCount, barSeries, avgCandleBodyIndicatorFunction));
		features.addAll(extractStochFeatures(index, barCount, barSeries, avgTrueRangeIndicatorFunction));
		features.addAll(extractStochFeatures(index, barCount, barSeries, standardDeviationIndicatorFunction));
		
		features.addAll(extractRatioFeatures(index, barCount, barSeries, volumeIndicatorFunction));
		features.addAll(extractRatioFeatures(index, barCount, barSeries, priceSpreadIndicatorFunction));
		features.addAll(extractRatioFeatures(index, barCount, barSeries, candleBodyIndicatorFunction));
		features.addAll(extractRatioFeatures(index, barCount, barSeries, trueRangeIndicatorFunction));
		features.addAll(extractRatioFeatures(index, barCount, barSeries, standardDeviationIndicatorFunction));
	}
	
	private Function<BarSeries, Indicator<Num>> getAverageIndicatorFunction (
			Function<BarSeries, Indicator<Num>> indicatorFunction){
		return series -> new SMAIndicator(indicatorFunction.apply(series), 5);
	}
	
	private List<Double> extractStochFeatures(int index, int barCount, BarSeries barSeries, Function<BarSeries, Indicator<Num>> function){
		fibonacciStochasticFeatureExtractor.setIndicatorFunction(function);
		return fibonacciStochasticFeatureExtractor.extract(index, barCount, barSeries);
	}
	
	private List<Double> extractRatioFeatures(int index, int barCount, BarSeries barSeries, Function<BarSeries, Indicator<Num>> function){
		ratioWithSmaFeatureExtractor.setIndicatorFunction(function);
		return ratioWithSmaFeatureExtractor.extract(index, barCount, barSeries);
	}
	
}
