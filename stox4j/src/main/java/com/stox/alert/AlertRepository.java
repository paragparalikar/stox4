package com.stox.alert;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class AlertRepository {
	
	private final Path home;
	private final Set<Alert> cache = new HashSet<>();
	
	private Path getPath() {
		return home.resolve("alerts.csv");
	}
	
	@SneakyThrows
	private void loadAllIfEmpty() {
		if(cache.isEmpty()) {
			final Path path = getPath();
			if(Files.exists(path)) {
				Files.lines(path)
					.map(this::fromString)
					.forEach(cache::add);
			}
		}
	}
	
	public Collection<Alert> findAll(){
		loadAllIfEmpty();
		return cache;
	}
	
	@SneakyThrows
	public void save(Alert alert) {
		loadAllIfEmpty();
		final Path path = getPath();
		if(!Files.exists(path)) {
			Files.createDirectories(path.getParent());
			Files.createFile(path);
		}
		Files.write(path, Collections.singleton(toString(alert) + System.lineSeparator()), 
				StandardOpenOption.WRITE, StandardOpenOption.APPEND, StandardOpenOption.CREATE);
		cache.add(alert);
	}
	
	@SneakyThrows
	public void delete(Alert alert) {
		cache.remove(alert);
		Files.write(getPath(), Collections.singleton(cache.stream()
				.map(this::toString)
				.collect(Collectors.joining(System.lineSeparator()))), 
				StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	private Alert fromString(String text) {
		final String[] tokens = text.split(",");
		return Alert.builder()
				.isin(tokens[0])
				.price(Double.parseDouble(tokens[1]))
				.build();
	}
	
	private String toString(Alert alert) {
		return String.join(",", alert.getIsin(), String.valueOf(alert.getPrice()));
	}
}
