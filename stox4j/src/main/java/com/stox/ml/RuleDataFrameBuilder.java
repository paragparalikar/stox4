package com.stox.ml;

import java.util.LinkedList;
import java.util.List;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;

import com.stox.ml.domain.Row;
import com.stox.ml.feature.BarSeriesFeatureExtractor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RuleDataFrameBuilder {
	
	private final BarSeriesFeatureExtractor barSeriesFeatureExtractor;
	
	public List<Row> build(int barCount, 
			Rule rule, 
			Rule liquidityRule,
			Rule classificationRule,
			Indicator<Integer> classIndicator, 
			BarSeries barSeries) {
		final List<Row> rows = new LinkedList<>();
		for(int index = barCount; index < barSeries.getBarCount() - barCount; index++) {
			if(liquidityRule.isSatisfied(index) && rule.isSatisfied(index)) {
				final List<Double> features = barSeriesFeatureExtractor.extract(index, barCount, barSeries);
				if(null != features) {
					final String label = String.valueOf(classificationRule.isSatisfied(index));
					rows.add(new Row(label, features));
				}
			}
		}
		return rows;
	}
	
}
