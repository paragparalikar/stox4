package com.stox.ml;

import java.util.ArrayList;
import java.util.List;

import org.ta4j.core.Bar;

import com.stox.common.bar.BarService;
import com.stox.example.Example;
import com.stox.example.ExampleService;

import lombok.RequiredArgsConstructor;
import smile.data.DataFrame;

@RequiredArgsConstructor
public class ExampleDataGenerator {

	private final BarService barService;
	private final ExampleService exampleService;
	private final BarValueNormalizer barValueNormalizer;
	
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
		for(int exampleIndex = 0; exampleIndex < examples.size(); exampleIndex++) {
			final Example example = examples.get(exampleIndex);
			final List<Bar> bars = barService.find(example.getIsin(), barCount, example.getTimestamp());
			if(bars.size() < barCount) continue;
			final double[] row = new double[colCount];
			final List<Bar> normalizedBars = barValueNormalizer.normalize(bars);
			row[0] = 1;
			for(int barIndex = 0; barIndex < normalizedBars.size(); barIndex++) {
				final Bar bar = normalizedBars.get(barIndex);
				row[barIndex * 5 + 1] = bar.getOpenPrice().doubleValue();
				row[barIndex * 5 + 2] = bar.getHighPrice().doubleValue();
				row[barIndex * 5 + 3] = bar.getLowPrice().doubleValue();
				row[barIndex * 5 + 4] = bar.getClosePrice().doubleValue();
				row[barIndex * 5 + 5] = bar.getVolume().doubleValue();
			}
			table.add(row);
		}
		
		final double[][] data = new double[table.size()][colCount];
		for(int colIndex = 0; colIndex < colCount; colIndex++) {
			for(int rowIndex = 0; rowIndex < table.size(); rowIndex++) {
				data[rowIndex][colIndex] = table.get(rowIndex)[colIndex];
			}
		}
		return data;
	}
	
}
