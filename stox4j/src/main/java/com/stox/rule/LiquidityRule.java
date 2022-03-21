package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;

import com.stox.screener.ScreenerConfig;

import lombok.Data;

public class LiquidityRule extends AbstractRule {
	
	@Data
	public static class LiquidityConfig implements ScreenerConfig {
		private int barCount = 55;
		private double minAmount = 10_00_000;
	}
	
	private final LiquidityConfig config;
	private final Indicator<Num> averageCloseIndicator;
	private final Indicator<Num> averageVolumeIndicator;
	
	public LiquidityRule(BarSeries barSeries, LiquidityConfig config) {
		this.config = config;
		final Indicator<Num> closePriceIndicator = new ClosePriceIndicator(barSeries);
		averageCloseIndicator = new SMAIndicator(closePriceIndicator, config.getBarCount());
		final VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
		averageVolumeIndicator = new SMAIndicator(volumeIndicator, config.getBarCount());
	}

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		final Num minAmount = DoubleNum.valueOf(config.getMinAmount());
		final Num averageVolume = averageVolumeIndicator.getValue(index);
		final Num averageClosePrice = averageCloseIndicator.getValue(index);
		return averageVolume.multipliedBy(averageClosePrice).isGreaterThanOrEqual(minAmount);
	}

}
