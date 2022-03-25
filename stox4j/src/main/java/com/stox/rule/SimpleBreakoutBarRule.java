package com.stox.rule;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;

import com.stox.indicator.PriceSpreadIndicator;
import com.stox.screener.ScreenerConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimpleBreakoutBarRule extends AbstractRule {
	
	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	public static class SimpleBreakoutBarRuleConfig implements ScreenerConfig {
		private int barCount = 5;
		private double maxSpreadMultiple = 5;
		private double maxHighDiffATRMultiple = 0.5;
		public String toString() {return String.format("BarCount: %d", barCount);}
	}

	@NonNull private final BarSeries barSeries;
	@NonNull private final SimpleBreakoutBarRuleConfig config;

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if(null != barSeries && config.getBarCount() < index && index < barSeries.getBarCount()) {
			return match(index) && !match(index - 1);
		}
		return false;
	}
	
	private boolean match(int index) {
		final int barCount = config.getBarCount();
		final Bar bar = barSeries.getBar(index);
		final Bar one = barSeries.getBar(index - 1);
		
		// Current bar volume should be more than previous bar volume
		if(bar.getVolume().isLessThan(one.getVolume())) return false;
		
		// Current bar close should be higher than previous bar close
		if(bar.getClosePrice().isLessThan(one.getHighPrice())) return false;
		
		// Current bar spread should be more than previous bar spread
		if(bar.getHighPrice().minus(bar.getLowPrice()).isLessThan(one.getHighPrice().minus(one.getLowPrice()))) return false;
		
		// Current bar spread should be more than multiple times average spread of last n bars
		final Num spread = bar.getHighPrice().minus(bar.getLowPrice());
		final Indicator<Num> priceSpreadIndicator = new PriceSpreadIndicator(barSeries);
		final Indicator<Num> spreadSMAIndicator = new SMAIndicator(priceSpreadIndicator, barCount);
		final Num avgSpread = spreadSMAIndicator.getValue(index - 1);
		final Num spreadMultiple = barSeries.numOf(config.getMaxSpreadMultiple());
		if(spread.isGreaterThan(avgSpread.multipliedBy(spreadMultiple))) return false;
		
		// Current bar high should be higher than previous bar by amount more than multiple times ATR of last n bars
		final Indicator<Num> atrIndicator = new ATRIndicator(barSeries, barCount);
		final Num atrValue = atrIndicator.getValue(index - 1);
		final Num atrMultiple = barSeries.numOf(config.getMaxHighDiffATRMultiple());
		final Num highDiff = bar.getHighPrice().minus(one.getHighPrice());
		if(highDiff.isLessThan(atrValue.multipliedBy(atrMultiple))) return false;
		
		return true;
	}

}
