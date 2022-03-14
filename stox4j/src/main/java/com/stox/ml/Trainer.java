package com.stox.ml;

import java.io.IOException;
import java.nio.file.Paths;

import com.stox.StoxApplicationContext;

import smile.data.DataFrame;
import smile.io.Write;

public class Trainer {

	public static void main(String[] args) throws IOException {
		final BarValueNormalizer normalizer = new BarValueNormalizer();
		final StoxApplicationContext context = new StoxApplicationContext();
		final ExampleDataGenerator dataGenerator = new ExampleDataGenerator(
				context.getBarService(), context.getExampleService(), normalizer);
		final DataFrame dataFrame = dataGenerator.generate("7a9a608f-308a-4d76-af01-7165f5d4918a", 144);
		Write.csv(dataFrame, Paths.get("C:\\Users\\parag\\.stox4j\\test.csv"));
		
		//final RandomForest randomForest = RandomForest.fit(null, dataFrame);
	}
	
}
