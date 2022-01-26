package com.stox.module.indicator;

import java.util.Arrays;
import java.util.List;

import com.stox.module.core.model.Bar;
import com.stox.module.core.model.BarValue;


public interface Indicator<T, V> {
	
	public static final List<Indicator<?,?>> ALL = Arrays.asList(
			new Stochastics(), new ExponentialMovingAverage(),
			new SimpleMovingAverage(), new RelativeStrengthIndex(), new BollingerBands(),
			new DecisionIndicator(), new RateOfChange(), new StandardDeviation(),
			new TrueVolatility(), new ZigZagIndicator(), new RelativeVolatility());
	
	public static <I> I ofType(final Class<I> type) {
		return ALL.stream().filter(type::isInstance).map(type::cast).findFirst().orElse(null);
	}

	public default Double getValue(final int index, final BarValue barValue, List<Double> values, List<Bar> bars) {
		return values.isEmpty() ? barValue.resolve(bars.get(index)) : values.get(index);
	}
	
	T defaultConfig();

	V compute(final List<Double> values, final List<Bar> bars, final T config);

	List<V> computeAll(final List<Double> values, final List<Bar> bars, final T config);

}
