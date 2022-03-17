package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;

public class LiquidityRule extends AbstractRule {
	
	private final Num minAmount;
	private final Indicator<Num> averageCloseIndicator;
	private final Indicator<Num> averageVolumeIndicator;
	
	public LiquidityRule(BarSeries barSeries, int barCount, double minAmount) {
		this.minAmount = barSeries.numOf(minAmount);
		final Indicator<Num> closePriceIndicator = new ClosePriceIndicator(barSeries);
		averageCloseIndicator = new SMAIndicator(closePriceIndicator, barCount);
		final VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
		averageVolumeIndicator = new SMAIndicator(volumeIndicator, barCount);
	}

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		final Num averageVolume = averageVolumeIndicator.getValue(index);
		final Num averageClosePrice = averageCloseIndicator.getValue(index);
		return averageVolume.multipliedBy(averageClosePrice).isGreaterThanOrEqual(minAmount);
	}

}
