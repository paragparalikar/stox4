package com.stox.mlx;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.BaseBarSeries;

public class BarSeriesNormalizer implements UnaryOperator<BarSeries> {

	@Override
	public BarSeries apply(BarSeries barSeries) {
		final List<Bar> bars = new ArrayList<>(barSeries.getBarCount());
		final BarNormalizer barNormalizer = new BarNormalizer();
		barSeries.getBarData().forEach(barNormalizer);
		for(int index = 0; index < barSeries.getBarCount(); index++) {
			final Bar bar = barSeries.getBar(index);
			final Bar nomralizedBar = barNormalizer.apply(bar);
			bars.add(nomralizedBar);
		}
		return new BaseBarSeries(bars);
	}
	
}
