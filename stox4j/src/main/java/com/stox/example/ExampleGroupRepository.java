package com.stox.example;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class ExampleGroupRepository {

	private final Path home;
	
	private Path getPath() {
		return home.resolve("examples").resolve("example-groups.csv");
	}
	
	@SneakyThrows
	public List<ExampleGroup> findAll(){
		final Path path = getPath();
		return Files.exists(path) ? 
				Files.lines(path).map(this::map).collect(Collectors.toList()) :
					Collections.emptyList();
	}
	
	@SneakyThrows
	public void saveAll(Collection<ExampleGroup> exampleGroups) {
		final List<String> lines = exampleGroups.stream()
			.map(this::map).collect(Collectors.toList());
		final Path path = getPath();
		Files.createDirectories(path.getParent());
		Files.write(path, lines, 
				StandardOpenOption.WRITE, 
				StandardOpenOption.CREATE, 
				StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	private ExampleGroup map(String line) {
		final String tokens[] = line.split(",");
		return new ExampleGroup(tokens[0], tokens[1]);
	}
	
	private String map(ExampleGroup exampleGroup) {
		return String.join(",", exampleGroup.getId(), exampleGroup.getName());
	}
	
}
