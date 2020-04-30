package com.stox.module.indicator;

import java.util.Arrays;
import java.util.List;

import com.stox.module.core.model.Bar;


public interface Indicator<T, V> {
	
	public static final List<Indicator<?,?>> ALL = Arrays.asList(
			new SimpleMovingAverage(), new RelativeStrengthIndex(), new BollingerBands(),
			new DecisionIndicator(), new RateOfChange(), new StandardDeviation(),
			new TrueVolatility(), new ZigZagIndicator());
	
	public static <I> I ofType(final Class<I> type) {
		return ALL.stream().filter(type::isInstance).map(type::cast).findFirst().orElse(null);
	}

	T defaultConfig();

	V compute(final List<Double> values, final List<Bar> bars, final T config);

	List<V> computeAll(final List<Double> values, final List<Bar> bars, final T config);

}
