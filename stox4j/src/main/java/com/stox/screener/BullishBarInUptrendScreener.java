package com.stox.screener;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.common.ui.ConfigView;
import com.stox.common.ui.form.auto.AutoForm;
import com.stox.mlx.ClassifierFactory;
import com.stox.rule.BullishBarInUpTrendRule;
import com.stox.screener.ScreenerConfig.FixedBarCountScreenerConfig;

import weka.classifiers.Classifier;

public class BullishBarInUptrendScreener implements Screener<FixedBarCountScreenerConfig> {
	
	@Override
	public String toString() {
		return "Bullish Bar in Uptrend";
	}

	@Override
	public FixedBarCountScreenerConfig createConfig() {
		return new FixedBarCountScreenerConfig(89);
	}

	@Override
	public ConfigView createConfigView(FixedBarCountScreenerConfig config) {
		return new AutoForm(config);
	}

	@Override
	public Rule createRule(FixedBarCountScreenerConfig config, BarSeries barSeries) {
		final String path = "C:\\Users\\parag\\Documents\\finance\\trading\\ml\\bullish-bar-D-stoplos_8-target_24-period_20-SMOTE.model";
		final Classifier classifier = ClassifierFactory.INSTANCE.getClassifier(path);
		return new BullishBarInUpTrendRule(barSeries, classifier);
	}

}
