package com.stox.ml.domain;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import weka.core.Attribute;
import weka.core.Instances;

@RequiredArgsConstructor
public class Table {

	private final List<Row> rows;
	
	@SneakyThrows
	public Instances toInstances() {
		final Instances instances = emptyInstances();
		
		rows.stream().map(Row::toInstance).forEach(instances::add);
		return instances;
	}
	
	public Instances emptyInstances() {
		final int featuresSize = rows.get(0).getFeatures().size();
		final ArrayList<Attribute> attributes = new ArrayList<>(featuresSize);
		for(int index = 0; index < featuresSize; index++) attributes.add(new Attribute("V"+index));
		final List<String> classes = Arrays.asList(Boolean.TRUE.toString(), Boolean.FALSE.toString());
		attributes.add(new Attribute("class", classes));
		final Instances instances = new Instances("Dataset", attributes, rows.size());
		instances.setClassIndex(featuresSize);
		return instances;
	}
	
	@SneakyThrows
	public void writeCsv(Path path) {
		Files.createDirectories(path.getParent());
		writeCsv(new FileWriter(path.toString()));
	}
	
	public String getHeaderCsv() {
		final int featuresSize = rows.get(0).getFeatures().size();
		final List<String> headers = new ArrayList<>(featuresSize);
		for(int index = 0; index < featuresSize; index++) headers.add("V"+index);
		headers.add("class");
		return String.join(",", headers);
	}
	
	@SneakyThrows
	public void writeCsv(Writer writer) {
		try (BufferedWriter bufferedWriter = new BufferedWriter(writer)){
			bufferedWriter.write(getHeaderCsv());
			bufferedWriter.newLine();
			for(Row row : rows) {
				bufferedWriter.write(row.toCsv());
				bufferedWriter.newLine();
			}
		}
	}
	
}
