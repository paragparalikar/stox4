package com.stox.ml;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.ta4j.core.Bar;
import org.ta4j.core.BaseBarSeries;

import com.stox.common.bar.BarService;
import com.stox.example.Example;
import com.stox.example.ExampleService;

import lombok.RequiredArgsConstructor;
import smile.data.DataFrame;

@RequiredArgsConstructor
public class ExampleDataGenerator {

	private final BarService barService;
	private final ExampleService exampleService;
	private final BarSeriesNormalizer barValueNormalizer;
	
	public DataFrame generate(String exampleGroupId, int barCount) {
		final List<Example> examples = new ArrayList<>(exampleService.findByGroupId(exampleGroupId));
		final double[][] data = extractNormalizedData(barCount, examples);
		return buildDataFrame(data);
	}
	
	private DataFrame buildDataFrame(double[][] data) {
		final String[] names = new String[data[0].length];
		names[0] = "c";
		for(int barIndex = 0, colIndex = 1; colIndex < data[0].length; barIndex++, colIndex+=5) {
			names[colIndex + 0] = "o"+barIndex;
			names[colIndex + 1] = "h"+barIndex;
			names[colIndex + 2] = "l"+barIndex;
			names[colIndex + 3] = "c"+barIndex;
			names[colIndex + 4] = "v"+barIndex;
		}
		return DataFrame.of(data, names);
	}
	
	private double[][] extractNormalizedData(int barCount, List<Example> examples) {
		final int colCount = 1 + barCount * 5;
		final ArrayList<double[]> table = new ArrayList<>();
		final Map<String, Set<ZonedDateTime>> cache = toMap(examples);
		for(String isin : cache.keySet()) {
			final Set<ZonedDateTime> timestamps = cache.get(isin);
			final List<Bar> bars = barService.find(isin, Integer.MAX_VALUE);
			if(bars.size() < barCount) continue;
			
			final List<Bar> normalizedBars = barValueNormalizer.normalize(new BaseBarSeries(bars)).getBarData();
			for(int offset = barCount; offset < normalizedBars.size(); offset++) {
				final List<Bar> subBars = normalizedBars.subList(offset - barCount, offset);
				final Bar latestBar = subBars.get(subBars.size() - 1);
				final boolean exists = timestamps.contains(latestBar.getEndTime());
				table.add(row(colCount, subBars, exists ? 1 : 0));
			}
		}
		return transform(colCount, table);
	}
	
	private double[] row(int colCount, List<Bar> bars, double classValue) {
		final double[] row = new double[colCount];
		row[0] = classValue;
		for(int barIndex = 0; barIndex < bars.size(); barIndex++) {
			final Bar bar = bars.get(barIndex);
			row[barIndex * 5 + 1] = bar.getOpenPrice().doubleValue();
			row[barIndex * 5 + 2] = bar.getHighPrice().doubleValue();
			row[barIndex * 5 + 3] = bar.getLowPrice().doubleValue();
			row[barIndex * 5 + 4] = bar.getClosePrice().doubleValue();
			row[barIndex * 5 + 5] = bar.getVolume().doubleValue();
		}
		return row;
	}
	
	private Map<String, Set<ZonedDateTime>> toMap(List<Example> examples){
		final Map<String, Set<ZonedDateTime>> map = new HashMap<>();
		for(Example example : examples) {
			final Set<ZonedDateTime> set = map.computeIfAbsent(example.getIsin(), key -> new HashSet<>());
			set.add(example.getTimestamp());
		}
		return map;
	}
	
	private double[][] transform(int colCount, ArrayList<double[]> table) {
		final double[][] data = new double[table.size()][colCount];
		for(int colIndex = 0; colIndex < colCount; colIndex++) {
			for(int rowIndex = 0; rowIndex < table.size(); rowIndex++) {
				data[rowIndex][colIndex] = table.get(rowIndex)[colIndex];
			}
		}
		return data;
	}
	
}
