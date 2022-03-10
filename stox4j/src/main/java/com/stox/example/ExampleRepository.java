package com.stox.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class ExampleRepository {

	private final Path home;
	private final DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
	
	private Path getPath(String groupId) {
		return home.resolve("examples").resolve(groupId);
	}
	
	@SneakyThrows
	public Set<Example> findByGroupId(String groupId){
		return Files.lines(getPath(groupId)).map(this::map).collect(Collectors.toSet());
	}
	
	@SneakyThrows
	public void saveAll(String groupId, Collection<Example> examples) {
		final Set<String> lines = examples.stream()
				.map(this::map).collect(Collectors.toSet());
		final Path path = getPath(groupId);
		Files.createDirectories(path.getParent());
		Files.write(path, lines, 
				StandardOpenOption.WRITE, 
				StandardOpenOption.CREATE, 
				StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	private Example map(String line) {
		final String[] tokens = line.split(",");
		final ZonedDateTime timestamp = ZonedDateTime.from(formatter.parse(tokens[2]));
		return new Example(tokens[0], tokens[1], timestamp);
	}
	
	private String map(Example example) {
		return String.join(",", example.getIsin(), example.getGroupId(), 
				formatter.format(example.getTimestamp()));
	}
}
