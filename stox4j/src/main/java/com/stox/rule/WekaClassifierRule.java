package com.stox.rule;

import java.util.Collections;
import java.util.List;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import com.stox.ml.domain.Row;
import com.stox.ml.domain.Table;
import com.stox.ml.feature.BarSeriesFeatureExtractor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import weka.classifiers.Classifier;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.SerializationHelper;

@Slf4j
public class WekaClassifierRule extends AbstractRule {

	private final int barCount;
	private final BarSeries barSeries;
	private volatile Classifier classifier;
	private final BarSeriesFeatureExtractor featureExtractor;
	
	@SneakyThrows
	public WekaClassifierRule(BarSeries barSeries, int barCount, 
			String modelPath, BarSeriesFeatureExtractor featureExtractor) {
		this.barCount = barCount;
		this.barSeries = barSeries;
		this.featureExtractor = featureExtractor;
		this.classifier = (Classifier) SerializationHelper.read(modelPath);
	}
	
	@Override
	@SneakyThrows
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		final List<Double> features = featureExtractor.extract(index, barCount, barSeries);
		if(null == features || features.isEmpty()) return false;
		final Row row = new Row(null, features);
		final Table table = new Table(Collections.singletonList(row));
		final Instances instances = table.toInstances();
		final Instance instance = instances.firstInstance();
		final double result = classifier.classifyInstance(instance);
		final String label = instance.classAttribute().value((int)result);
		return Boolean.parseBoolean(label);
	}

}
