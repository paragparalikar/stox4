package com.stox.mlx;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.ta4j.core.BarSeries;
import org.ta4j.core.Indicator;
import org.ta4j.core.indicators.ATRIndicator;
import org.ta4j.core.indicators.ROCIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.adx.ADXIndicator;
import org.ta4j.core.indicators.helpers.PreviousValueIndicator;
import org.ta4j.core.indicators.helpers.TypicalPriceIndicator;
import org.ta4j.core.indicators.statistics.StandardDeviationIndicator;
import org.ta4j.core.num.Num;

import com.stox.indicator.ATRRatioIndicator;
import com.stox.indicator.CloseChangeRatioIndicator;
import com.stox.indicator.HighChangeRatioIndicator;
import com.stox.indicator.SMARatioIndicator;
import com.stox.indicator.SpreadRatioIndicator;
import com.stox.indicator.TRRatioIndicator;
import com.stox.indicator.VolatilityIndicator;
import com.stox.indicator.VolumeRatioIndicator;

import weka.core.Attribute;

public class BarSeriesFeatureExtractor {
	private static final int baseLength = 8;
	private static final int[] lengths = {5, 8, 13, 21, 34, 55, 89};
	private static final ArrayList<Attribute> attributes = new ArrayList<>();
	
	private final BarSeries barSeries;
	private final List<Indicator<Num>> indicators = new LinkedList<>();
	
	public BarSeriesFeatureExtractor(BarSeries barSeries) {
		this.barSeries = barSeries;
		createFeatureIndicators();
		for(int index = 1; index < 3; index++) createPreviousValueIndicators(index);
		for(int length : lengths) createFeatureIndicators(length);
		if(attributes.isEmpty()) getAttributeNames().stream().map(Attribute::new).forEach(attributes::add);
	}
	
	private List<String> getAttributeNames(){
		final List<String> attributes = new LinkedList<>();
		for(int index = 0; index < 3; index++) {
			attributes.add(String.format("TRRatio8[%d]", index));
			attributes.add(String.format("ATRRatio8[%d]", index));
			attributes.add(String.format("SMARatio8[%d]", index));
			attributes.add(String.format("SpreadRatio8[%d]", index));
			attributes.add(String.format("VolumeRatio8[%d]", index));
			attributes.add(String.format("HighChangeRatio8[%d]", index));
			attributes.add(String.format("CloseChangeRatio8[%d]", index));
		}
		for(int length : lengths) {
			attributes.add(String.format("PrevADX%d", length));
			attributes.add(String.format("PrevATRRatio%d", length));
			attributes.add(String.format("PrevSMARatio%d", length));
			attributes.add(String.format("PrevVolatility%d", length));
			attributes.add(String.format("PrevRSI%d", length));
			attributes.add(String.format("PrevROC%d", length));
			attributes.add(String.format("PrevStoch%d", length));
			attributes.add(String.format("PrevRSIATR%d", length));
			attributes.add(String.format("PrevRSIStdDev%d", length));
		}
		return attributes;
	}
	
	private void createFeatureIndicators() {
		indicators.add(new TRRatioIndicator(barSeries, baseLength));
		indicators.add(new ATRRatioIndicator(barSeries, baseLength));
		indicators.add(new SMARatioIndicator(barSeries, baseLength));
		indicators.add(new SpreadRatioIndicator(barSeries, baseLength));
		indicators.add(new VolumeRatioIndicator(barSeries, baseLength));
		indicators.add(new HighChangeRatioIndicator(barSeries, baseLength));
		indicators.add(new CloseChangeRatioIndicator(barSeries, baseLength));
	}
	
	private void createPreviousValueIndicators(int offset) {
		indicators.add(new PreviousValueIndicator(new TRRatioIndicator(barSeries, baseLength), offset));
		indicators.add(new PreviousValueIndicator(new ATRRatioIndicator(barSeries, baseLength), offset));
		indicators.add(new PreviousValueIndicator(new SMARatioIndicator(barSeries, baseLength), offset));
		indicators.add(new PreviousValueIndicator(new SpreadRatioIndicator(barSeries, baseLength), offset));
		indicators.add(new PreviousValueIndicator(new VolumeRatioIndicator(barSeries, baseLength), offset));
		indicators.add(new PreviousValueIndicator(new HighChangeRatioIndicator(barSeries, baseLength), offset));
		indicators.add(new PreviousValueIndicator(new CloseChangeRatioIndicator(barSeries, baseLength), offset));
	
	}
	
	private void createFeatureIndicators(int length) {
		final TypicalPriceIndicator typicalPriceIndicator = new TypicalPriceIndicator(barSeries);
		indicators.add(new PreviousValueIndicator(new ADXIndicator(barSeries, length)));
		indicators.add(new PreviousValueIndicator(new ATRRatioIndicator(barSeries, length)));
		indicators.add(new PreviousValueIndicator(new SMARatioIndicator(barSeries, length)));
		indicators.add(new PreviousValueIndicator(new VolatilityIndicator(barSeries, length)));
		indicators.add(new PreviousValueIndicator(new RSIIndicator(typicalPriceIndicator, length)));
		indicators.add(new PreviousValueIndicator(new ROCIndicator(typicalPriceIndicator, length)));
		indicators.add(new PreviousValueIndicator(new StochasticOscillatorKIndicator(barSeries, length)));
		indicators.add(new PreviousValueIndicator(new RSIIndicator(new ATRIndicator(barSeries, baseLength), length)));
		indicators.add(new PreviousValueIndicator(new RSIIndicator(new StandardDeviationIndicator(typicalPriceIndicator, baseLength), length)));
	}
	
	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}

	public List<Double> extract(int index){
		return indicators.stream()
				.map(indicator -> indicator.getValue(index))
				.map(Num::doubleValue)
				.collect(Collectors.toList());
	}
	
}
