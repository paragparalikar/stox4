package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Position;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;

public class ATRStopLossRule extends AbstractRule {

	private final Num atrMultiple;
	private final ATRIndicator atrIndicator;
	private final ClosePriceIndicator closePriceIndicator;

	public ATRStopLossRule(BarSeries barSeries, int barCount, Number atrMultiple) {
		this.atrIndicator = new ATRIndicator(barSeries, barCount);
		this.closePriceIndicator = new ClosePriceIndicator(barSeries);
		this.atrMultiple = atrIndicator.numOf(atrMultiple);
	}

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if (null != tradingRecord) {
			final Position currentPosition = tradingRecord.getCurrentPosition();
			if (currentPosition.isOpened()) {
				final Num atr = atrIndicator.getValue(index);
				final Num entryPrice = currentPosition.getEntry().getNetPrice();
				final Num currentPrice = closePriceIndicator.getValue(index);
				return currentPosition.getEntry().isBuy() ? 
						isBuyStopSatisfied(entryPrice, currentPrice, atr) :
						isSellStopSatisfied(entryPrice, currentPrice, atr);
			}
		}
		return false;
	}

	private boolean isSellStopSatisfied(Num entryPrice, Num currentPrice, Num atr) {
		return currentPrice.isGreaterThan(entryPrice.plus(atr.multipliedBy(atrMultiple)));
	}

	private boolean isBuyStopSatisfied(Num entryPrice, Num currentPrice, Num atr) {
		return currentPrice.isLessThan(entryPrice.minus(atr.multipliedBy(atrMultiple)));
	}

}
