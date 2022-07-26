package com.stox.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;

import weka.classifiers.Classifier;

public class BullishBarInUpTrendRule extends AbstractMLRule {

	private final Rule delegate;
	
	public BullishBarInUpTrendRule(BarSeries barSeries, Classifier classifier) {
		super(barSeries, classifier);
		this.delegate = new BullishBarRule(barSeries)
				.and(new LiquidityRule(barSeries, 15, 1_00_00_000))
				.and(new NotOverboughtByRSIRule(8, 70, barSeries))
				.and(new NotOverboughtByPriceChannelRule(21, barSeries))
				.and(new SMAUpTrendRule(barSeries, 50));
	}

	@Override
	protected boolean isSatisfiedInternal(int index, TradingRecord tradingRecord) {
		return delegate.isSatisfied(index, tradingRecord);
	}

}
