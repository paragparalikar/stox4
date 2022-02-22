package com.stox.rule;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@RequiredArgsConstructor
public class ReaccumulationRule extends AbstractRule {

	private final BarSeries barSeries;
	private final ReaccumulationRuleConfig config;
	
	@Value
	public static class ReaccumulationRuleConfig {
		private int barCount = 55;
	}

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		final int barCount = config.getBarCount();
		if(index < barCount || index >= barSeries.getBarCount()) return false;
		Bar highestHighBar = null, lowestLowBar = null, currentBar = barSeries.getBar(index);
		int highestHighBarIndex = index, lowestLowBarIndex = index;
		for(int barIndex = index; barIndex > index - barCount; barIndex--) {
			final Bar bar = barSeries.getBar(barIndex);
			if(null == highestHighBar || highestHighBar.getHighPrice().isLessThanOrEqual(bar.getHighPrice())) {
				highestHighBar = bar;
				highestHighBarIndex = barIndex;
			}
			if(null == lowestLowBar || lowestLowBar.getLowPrice().isGreaterThanOrEqual(bar.getLowPrice())) {
				lowestLowBar = bar;
				lowestLowBarIndex = barIndex;
			}
		}
		
		if(highestHighBarIndex >= index) return false;
		if(lowestLowBarIndex >= highestHighBarIndex) return false;
		if(null == highestHighBar || null == lowestLowBar) return false;
		if(highestHighBar.getHighPrice().isLessThanOrEqual(currentBar.getHighPrice())) return false;
		if(highestHighBar.getHighPrice().isLessThanOrEqual(lowestLowBar.getHighPrice())) return false;
		if(lowestLowBar.getLowPrice().isGreaterThanOrEqual(currentBar.getLowPrice())) return false;
		
		// up move bar count should be less than down move bar count
		// up move change per bar should be multiple * down move change per bar
		
		return true;
	}

}
