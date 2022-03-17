package com.stox.ml;

import java.util.LinkedList;
import java.util.List;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Rule;

import com.stox.ml.indicator.BuyTradeClassIndicator;

import lombok.RequiredArgsConstructor;
import smile.data.Tuple;

@RequiredArgsConstructor
public class RuleDataFrameBuilder {
	
	public List<Tuple> build(int barCount, Rule rule, BarSeries barSeries, BuyTradeClassIndicator classIndicator) {
		final List<Tuple> tuples = new LinkedList<>();
		for(int index = barCount; index < barSeries.getBarCount() - barCount; index++) {
			if(rule.isSatisfied(index)) {
				final int class_ = classIndicator.getValue(index);
				final Tuple tuple = BarSeriesTuple.builder()
						.index(index)
						.class_(class_)
						.barCount(barCount)
						.barSeries(barSeries)
						.build();
				tuples.add(tuple);
			}
		}
		return tuples;
	}
	
}
