package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.rules.OverIndicatorRule;

import com.stox.indicator.ProductIndicator;
import com.stox.screener.ScreenerConfig;

import lombok.Data;

public class LiquidityRule extends AbstractRule {
	
	@Data
	public static class LiquidityConfig implements ScreenerConfig {
		private int barCount = 55;
		private double minAmount = 100_00_000;
	}
	
	private final Rule delegate;
	
	public LiquidityRule(BarSeries barSeries, int length, double minimumTurnover) {
		final VolumeIndicator volumeIndicator = new VolumeIndicator(barSeries);
		final TypicalPriceIndicator typicalPriceIndicator = new TypicalPriceIndicator(barSeries);
		final ProductIndicator productIndicator = new ProductIndicator(volumeIndicator, typicalPriceIndicator);
		final SMAIndicator smaIndicator = new SMAIndicator(productIndicator, length);
		this.delegate = new OverIndicatorRule(smaIndicator, minimumTurnover);
	}

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		return delegate.isSatisfied(index);
	}

}
