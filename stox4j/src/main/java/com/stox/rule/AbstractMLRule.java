package com.stox.rule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.ta4j.core.BarSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.rules.AbstractRule;

import com.stox.mlx.BarSeriesFeatureExtractor;
import com.stox.mlx.BarSeriesNormalizer;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import weka.classifiers.Classifier;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instance;
import weka.core.Instances;

@RequiredArgsConstructor
public abstract class AbstractMLRule extends AbstractRule {

	private final BarSeries barSeries;
	private final Classifier classifier;
	private static final int length = 89;
	private final BarSeriesNormalizer barSeriesNormalizer = new BarSeriesNormalizer();
	
	@Override
	@SneakyThrows
	public boolean isSatisfied(int index, TradingRecord tradingRecord) {
		if(length < index && index < barSeries.getBarCount() && isSatisfiedInternal(index, tradingRecord)) {
			final BarSeries subSeries = barSeries.getSubSeries(index - length, index + 1);
			final BarSeries normalizedSubSeries = barSeriesNormalizer.apply(subSeries);
			final BarSeriesFeatureExtractor featureExtractor = new BarSeriesFeatureExtractor(normalizedSubSeries);
			
			final List<Double> features = new ArrayList<>();
			features.addAll(extractFeatures(normalizedSubSeries));
			features.addAll(featureExtractor.extract(normalizedSubSeries.getEndIndex()));
			final double[] values = features.stream().mapToDouble(Double::doubleValue).toArray();
			
			final Attribute classAttribute = new Attribute("Success", Arrays.asList("TRUE", "FALSE"));
			final ArrayList<Attribute> attributes = new ArrayList<>();
			attributes.addAll(getAttributes());
			attributes.addAll(featureExtractor.getAttributes());
			attributes.add(classAttribute);
			final Instances instances = new Instances("default", attributes, 1);
			instances.setClass(classAttribute);
			final Instance instance = new DenseInstance(1.0, values);
			instance.setDataset(instances);
			final double classValue = classifier.classifyInstance(instance);
			return 0 == classValue;
		}
		return false;
	}
	
	protected List<Attribute> getAttributes(){
		return Collections.emptyList();
	}
	
	protected List<Double> extractFeatures(BarSeries barSeries) {
		return Collections.emptyList();
	}
	
	protected abstract boolean isSatisfiedInternal(int index, TradingRecord tradingRecord);
}
