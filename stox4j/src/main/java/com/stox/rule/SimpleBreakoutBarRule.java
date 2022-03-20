package com.stox.rule;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.VolumeIndicator;
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
		private double volumeMultiple = 5;
		private double spreadMultiple = 5;
		private double highDiffATRMultiple = 0.5;
	}

	@NonNull private final BarSeries barSeries;
	@NonNull private final SimpleBreakoutBarRuleConfig config;

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if(null != barSeries && config.getBarCount() + 1 <= index && index < barSeries.getBarCount()) {
			return match(index) && !match(index - 1);
		}
		return false;
	}
	
	private boolean match(int index) {
		final int barCount = config.getBarCount();
		final Bar bar = barSeries.getBar(index);
		final Bar one = barSeries.getBar(index - 1);
		if(bar.getVolume().isLessThan(one.getVolume())) return false;
		if(bar.getClosePrice().isLessThan(one.getHighPrice())) return false;
		if(bar.getHighPrice().minus(bar.getLowPrice()).isLessThan(one.getHighPrice().minus(one.getLowPrice()))) return false;
	//	if(bar.getClosePrice().minus(bar.getOpenPrice()).abs().isLessThan(one.getClosePrice().minus(one.getOpenPrice()).abs())) return false;
		
		final Indicator<Num> volumeIndicator = new VolumeIndicator(barSeries);
		final Indicator<Num> volumeSMAIndicator = new SMAIndicator(volumeIndicator, barCount);
		final Num avgVolume = volumeSMAIndicator.getValue(index);
		final Num volumeMultiple = barSeries.numOf(config.getVolumeMultiple());
		if(bar.getVolume().isGreaterThan(avgVolume.multipliedBy(volumeMultiple))) return false;
		
		final Num spread = bar.getHighPrice().minus(bar.getLowPrice());
		final Indicator<Num> priceSpreadIndicator = new PriceSpreadIndicator(barSeries);
		final Indicator<Num> spreadSMAIndicator = new SMAIndicator(priceSpreadIndicator, barCount);
		final Num avgSpread = spreadSMAIndicator.getValue(index);
		final Num spreadMultiple = barSeries.numOf(config.getSpreadMultiple());
		if(spread.isGreaterThan(avgSpread.multipliedBy(spreadMultiple))) return false;
		
		final Indicator<Num> atrIndicator = new ATRIndicator(barSeries, barCount);
		final Num atrValue = atrIndicator.getValue(index);
		final Num atrMultiple = barSeries.numOf(config.getHighDiffATRMultiple());
		final Num highDiff = bar.getHighPrice().minus(one.getHighPrice());
		if(highDiff.isLessThan(atrValue.multipliedBy(atrMultiple))) return false;
		
		return true;
	}

}
