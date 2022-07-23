package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.indicators.helpers.DifferenceIndicator;
import org.ta4j.core.indicators.helpers.HighPriceIndicator;
import org.ta4j.core.indicators.helpers.LowPriceIndicator;
import org.ta4j.core.indicators.helpers.PreviousValueIndicator;
import org.ta4j.core.indicators.helpers.SumIndicator;
import org.ta4j.core.indicators.helpers.TransformIndicator;
import org.ta4j.core.indicators.statistics.SimpleLinearRegressionIndicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.rules.AbstractRule;
import org.ta4j.core.rules.OverIndicatorRule;

public class TestBreakoutBarRule extends AbstractRule {
	
	private final Rule delegate;
	
	public TestBreakoutBarRule(BarSeries series) {
		final int barCount = 5;
		final Num multiple = DoubleNum.valueOf(2);
		final Num atrMultiple = DoubleNum.valueOf(1);
		
		final Indicator<Num> atrIndicator = new ATRIndicator(series, barCount);
		final Indicator<Num> atrMultipleIndicator = new TransformIndicator(atrIndicator, atrMultiple::multipliedBy);
		final Indicator<Num> highPriceIndicator = new HighPriceIndicator(series);
		final Indicator<Num> highRegressionIndicator = new SimpleLinearRegressionIndicator(highPriceIndicator, barCount);
		final Indicator<Num> regressionHighPlusAtrMultipleIndicator = new SumIndicator(highRegressionIndicator, atrMultipleIndicator);
		final Indicator<Num> previousRegressionHighPlusAtrMultipleIndicator = new PreviousValueIndicator(regressionHighPlusAtrMultipleIndicator, 1);
		final Rule highRule = new OverIndicatorRule(highPriceIndicator, previousRegressionHighPlusAtrMultipleIndicator);
		
		final Indicator<Num> lowPriceIndicator = new LowPriceIndicator(series);
		final Indicator<Num> priceSpreadIndicator = new DifferenceIndicator(highPriceIndicator, lowPriceIndicator);
		final Indicator<Num> priceSpreadRegressionIndicator = new SimpleLinearRegressionIndicator(priceSpreadIndicator, barCount);
		final Indicator<Num> regressedSpreadMultipleIndicator = new TransformIndicator(priceSpreadRegressionIndicator, multiple::multipliedBy);
		final Rule spreadRule = new OverIndicatorRule(priceSpreadIndicator, regressedSpreadMultipleIndicator);
		
		final Indicator<Num> closePriceIndicator = new ClosePriceIndicator(series);
		final Indicator<Num> previousCloseIndicator = new PreviousValueIndicator(closePriceIndicator);
		final Indicator<Num> closeChangeIndicator = new DifferenceIndicator(closePriceIndicator, previousCloseIndicator);
		final Indicator<Num> absoluteCloseChangeIndicator = new TransformIndicator(closeChangeIndicator, Num::abs);
		final Indicator<Num> regressedAbsoluteCloseChangeIndicator = new SimpleLinearRegressionIndicator(absoluteCloseChangeIndicator, barCount);
		final Rule closeChangeRule = new OverIndicatorRule(closeChangeIndicator, regressedAbsoluteCloseChangeIndicator);
		
		this.delegate = highRule;
	}

	@Override
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		return delegate.isSatisfied(index, tradingRecord);
	}

}
