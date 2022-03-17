package com.stox.ml.rule;

import org.ta4j.core.Bar;
import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BuyTradeSuccessRule extends AbstractRule {

	private final BarSeries barSeries;
	private final int barCount;
	private final double minGainPercentage;
	private final double maxLossPercentage;
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if(index < barSeries.getBarCount() - barCount) {
			final Num close = barSeries.getBar(index).getClosePrice();
			final Num target = computeTarget(close, barSeries.numOf(minGainPercentage));
			final Num stopLoss = computeStopLoss(close, barSeries.numOf(maxLossPercentage));
			for(int i = index + 1; i < index + barCount + 1; i++) {
				final Bar bar = barSeries.getBar(i);
				if(stopLoss.isGreaterThanOrEqual(bar.getLowPrice())) return false;
				if(target.isLessThanOrEqual(bar.getHighPrice())) return true;
			}
		}
		return false;
	}
	
	
	private Num computeTarget(Num close, Num percentage) {
		final Num hundred = DoubleNum.valueOf(100);
		return close.multipliedBy(percentage.plus(hundred).dividedBy(hundred));
	}
	
	private Num computeStopLoss(Num close, Num percentage) {
		final Num hundred = DoubleNum.valueOf(100);
		return close.multipliedBy(hundred.minus(percentage).dividedBy(hundred));
	}

}
