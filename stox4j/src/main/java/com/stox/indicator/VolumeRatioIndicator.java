package com.stox.indicator;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.CachedIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.PreviousValueIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.num.Num;

public class VolumeRatioIndicator extends CachedIndicator<Num> {

	private final Indicator<Num> delegate;
	
	public VolumeRatioIndicator(BarSeries series, int barCount) {
		super(series);
		final VolumeIndicator volumeIndicator = new VolumeIndicator(series);
		final SMAIndicator averageVolumeIndicator = new SMAIndicator(volumeIndicator, barCount);
		final Indicator<Num> previsouAvgVolumeIndicator = new PreviousValueIndicator(averageVolumeIndicator);
		this.delegate = new RatioIndicator(volumeIndicator, previsouAvgVolumeIndicator);
	}

	@Override
	protected Num calculate(int index) {
		return delegate.getValue(index);
	}

}
