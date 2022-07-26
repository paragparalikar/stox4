package com.stox.charting.plot.rule;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;

import com.stox.indicator.RuleIndicator;
import com.stox.rule.BullishBarInUpTrendRule;

import lombok.SneakyThrows;
import weka.classifiers.Classifier;
import weka.core.SerializationHelper;

public class PlottableBullishBarInUptrendRule extends AbstractPlottableRule<Void> {

	private volatile Classifier classifier;
	
	@Override
	public String toString() {
		return "Bullish Bar in Uptrend";
	}
	
	@Override
	public Void createConfig() {
		return null;
	}

	@Override
	@SneakyThrows
	public Indicator<Boolean> createIndicator(Void config, BarSeries barSeries) {
		if(null == classifier) {
			String path = "C:\\Users\\parag\\Documents\\finance\\trading\\ml\\bullish-bar-D-stoplos_8-target_24-period_20-SMOTE.model";
			classifier = (Classifier) SerializationHelper.read(path);
		}
		return new RuleIndicator(new BullishBarInUpTrendRule(barSeries, classifier), barSeries);
	}

}
