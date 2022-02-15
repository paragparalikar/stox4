package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Position;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.HighestValueIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.LowestValueIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;

public class TrailingATRStopLossRule extends AbstractRule {
	
	private final Num atrMultiple;
	private final ATRIndicator atrIndicator;
	private final ClosePriceIndicator closePriceIndicator;
	private final HighPriceIndicator highPriceIndicator;
	private final LowPriceIndicator lowPriceIndicator;

	public TrailingATRStopLossRule(BarSeries barSeries, int barCount, Number atrMultiple) {
		this.atrIndicator = new ATRIndicator(barSeries, barCount);
		this.lowPriceIndicator = new LowPriceIndicator(barSeries);
		this.closePriceIndicator = new ClosePriceIndicator(barSeries);
		this.highPriceIndicator = new HighPriceIndicator(barSeries);
		this.atrMultiple = barSeries.numOf(atrMultiple);
	}
	
	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if (null != tradingRecord) {
			final Position currentPosition = tradingRecord.getCurrentPosition();
			if (currentPosition.isOpened()) {
				final Num atr = atrIndicator.getValue(index);
				final Num currentPrice = closePriceIndicator.getValue(index);
				final int barCount = index - currentPosition.getEntry().getIndex() + 1;
				return currentPosition.getEntry().isBuy() ? 
						isBuyStopSatisfied(index, barCount, currentPrice, atr) :
						isSellStopSatisfied(index, barCount, currentPrice, atr);
			}
		}
		return false;
	}
	
	private boolean isSellStopSatisfied(int index, int barCount, Num currentPrice, Num atr) {
		final LowestValueIndicator lowestValueIndicator = new LowestValueIndicator(lowPriceIndicator, barCount);
		final Num lowestLowValue = lowestValueIndicator.getValue(index);
		return currentPrice.isGreaterThan(lowestLowValue.plus(atr.multipliedBy(atrMultiple)));
	}

	private boolean isBuyStopSatisfied(int index, int barCount, Num currentPrice, Num atr) {
		final HighestValueIndicator highestValueIndicator = new HighestValueIndicator(highPriceIndicator, barCount);
		final Num highestHighValue = highestValueIndicator.getValue(index);
		return currentPrice.isLessThan(highestHighValue.minus(atr.multipliedBy(atrMultiple)));
	}

}
