package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;

public class PriceRejectionRule extends AbstractRule {

	private final int barCount;
	private final Num atrMultiple;
	private final BarSeries barSeries;
	private final ATRIndicator atrIndicator;
	
	public PriceRejectionRule(BarSeries barSeries, int barCount, Number atrMultiple) {
		this.barCount = barCount;
		this.barSeries = barSeries;
		this.atrMultiple = barSeries.numOf(atrMultiple);
		this.atrIndicator = new ATRIndicator(barSeries, barCount);
	}
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		int lowestLowIndex = 0;
		Num lowestLow = DoubleNum.valueOf(Double.MAX_VALUE);
		Num formerHigh = DoubleNum.valueOf(Double.MIN_VALUE);
		Num laterHigh = DoubleNum.valueOf(Double.MIN_VALUE);
		
		for(int i = index; i > index - barCount; i--) {
			final Num low = barSeries.getBar(i).getLowPrice();
			if(low.isLessThan(lowestLow)) {
				lowestLow = low;
				lowestLowIndex = i;
			}
		}
		
		for(int i = lowestLowIndex; i > index - barCount; i--) {
			formerHigh = formerHigh.max(barSeries.getBar(i).getHighPrice());
		}
		
		for(int i = index; i >= lowestLowIndex; i--) {
			laterHigh = laterHigh.max(barSeries.getBar(i).getHighPrice());
		}
		
		final Num distance = formerHigh.plus(laterHigh).minus(lowestLow.multipliedBy(DoubleNum.valueOf(2)));
		final Num atr = atrIndicator.getValue(index);
		
		return distance.isGreaterThanOrEqual(atr.multipliedBy(atrMultiple));
	}
	
}
