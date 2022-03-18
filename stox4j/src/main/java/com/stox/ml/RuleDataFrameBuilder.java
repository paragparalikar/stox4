package com.stox.ml;

import java.util.LinkedList;
import java.util.List;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;

import lombok.RequiredArgsConstructor;
import smile.data.Tuple;

@RequiredArgsConstructor
public class RuleDataFrameBuilder {
	
	public List<Tuple> build(int barCount, 
			Rule rule, 
			Rule liquidityRule,
			Rule classificationRule,
			Indicator<Integer> classIndicator, 
			BarSeries barSeries) {
		final List<Tuple> tuples = new LinkedList<>();
		for(int index = barCount; index < barSeries.getBarCount() - barCount; index++) {
			if(liquidityRule.isSatisfied(index) && rule.isSatisfied(index)) {
				final Integer class_ = classificationRule.isSatisfied(index) ? 
						classIndicator.getValue(index) : Integer.valueOf(-1);
				if(null != class_) {
					final Tuple tuple = BarSeriesTuple.builder()
							.index(index)
							.class_(class_)
							.barCount(barCount)
							.barSeries(barSeries)
							.build();
					tuples.add(tuple);
				}
			}
		}
		return tuples;
	}
	
}
