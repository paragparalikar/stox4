package com.stox.rule;

import java.util.List;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import com.stox.ml.feature.BarSeriesFeatureExtractor;

import lombok.SneakyThrows;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.SerializationHelper;

public class AbstractMachineLearningRule extends AbstractRule {

	private final int barCount;
	private final BarSeries barSeries;
	private volatile Classifier classifier;
	private final BarSeriesFeatureExtractor featureExtractor;
	
	public AbstractMachineLearningRule(BarSeries barSeries, int barCount, 
			String modelPath, BarSeriesFeatureExtractor featureExtractor) {
		this.barCount = barCount;
		this.barSeries = barSeries;
		this.featureExtractor = featureExtractor;
		new Thread(() -> loadModel(modelPath)).start();
	}
	
	@SneakyThrows
	private void loadModel(String path) {
		this.classifier = (Classifier) SerializationHelper.read(path);
	}
	
	@Override
	@SneakyThrows
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		final List<Double> features = featureExtractor.extract(index, barCount, barSeries);
		if(null == features || features.isEmpty()) return false;
		final double[] featureArray = features.stream().mapToDouble(Double::doubleValue).toArray();
		final Instance instance = new DenseInstance(1, featureArray);
		final double result = classifier.classifyInstance(instance);
		final String label = instance.classAttribute().value((int)result);
		return Boolean.parseBoolean(label);
	}

}
