package com.stox.ml;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import com.stox.StoxApplicationContext;

import smile.classification.DataFrameClassifier;
import smile.data.DataFrame;
import smile.data.formula.Formula;

public class Trainer {

	public static void main(String[] args) throws IOException, URISyntaxException {
		final BarValueNormalizer normalizer = new BarValueNormalizer();
		final StoxApplicationContext context = new StoxApplicationContext();
		final ExampleDataGenerator dataGenerator = new ExampleDataGenerator(
				context.getBarService(), context.getExampleService(), normalizer);
		final Formula formula = Formula.lhs("c");
		final DataFrame dataFrame = dataGenerator.generate("7a9a608f-308a-4d76-af01-7165f5d4918a", 8);
		//Write.csv(dataFrame, Paths.get("C:\\Users\\parag\\.stox4j\\test.csv"));
		final DataFrameClassifier model = null;
		final int[] results = model.predict(dataFrame);
		report(results);
	}
	
	private static void report(int[] labels) {
		final Map<Integer, Integer> results = new HashMap<>();
		for(int label : labels) {
			final int occurances = results.getOrDefault(label, 0);
			results.put(label, occurances + 1);
		}
		for(int key : results.keySet()) {
			System.out.println(String.format("Label : %d, Occurances : %d", key, results.get(key)));
		}
	}
	
}
