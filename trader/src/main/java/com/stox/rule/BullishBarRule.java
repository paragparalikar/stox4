package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BullishBarRule extends AbstractRule {

	private final BarSeries barSeries;
	private final int barCount;
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		return isBullishBar(index) && !isBullishBar(index - 1);
	}
	
	private boolean isBullishBar(int index) {
		if(barCount + 1 < index) {
			Num highestHigh = barSeries.numOf(Double.MIN_VALUE);
			for(int i = index - 1; i >= index - 1 - barCount; i--) {
				highestHigh = highestHigh.max(barSeries.getBar(i).getHighPrice());
			}
			return barSeries.getBar(index).getClosePrice().isGreaterThan(highestHigh);
		}
		return false;
	}

}
