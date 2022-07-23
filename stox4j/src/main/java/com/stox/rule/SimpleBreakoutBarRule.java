package com.stox.rule;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.rules.UnderIndicatorRule;

import com.stox.screener.ScreenerConfig;
import com.stox.screener.ScreenerConfig.FixedBarCountScreenerConfig;

import lombok.Getter;

public class SimpleBreakoutBarRule extends AbstractRule {

	private final Rule delegate;
	private final BarSeries barSeries;
	@Getter private final ScreenerConfig config = new FixedBarCountScreenerConfig(21);
	
	public SimpleBreakoutBarRule(BarSeries barSeries) {
		this.barSeries = barSeries;
		final HighPriceIndicator highPriceIndicator = new HighPriceIndicator(barSeries);
		final ClosePriceIndicator closePriceIndicator = new ClosePriceIndicator(barSeries);
		final RSIIndicator rsiIndicator = new RSIIndicator(closePriceIndicator, 5);
		final HighestValueIndicator highestHighIndicator = new HighestValueIndicator(highPriceIndicator, 21);
		this.delegate = new UnderIndicatorRule(rsiIndicator, 70)
				.and(new UnderIndicatorRule(highPriceIndicator, highestHighIndicator));
	}
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if(null != barSeries && 21 < barSeries.getBarCount()) {
			return match(index) && !match(index - 1) && delegate.isSatisfied(index, tradingRecord);
		}
		return false;
	}
	
	private boolean match(int index) {
		final Bar bar = barSeries.getBar(index);
		final Bar one = barSeries.getBar(index - 1);
		return bar.getClosePrice().isGreaterThanOrEqual(one.getHighPrice());
	}

}
