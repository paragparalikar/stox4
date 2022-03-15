package com.stox.common.scrip;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class ScripRepository {

	private final Path home;
	
	private Path getPath() {
		return home.resolve(Paths.get("exchanges", "nse", "scrips.csv"));
	}
	
	@SneakyThrows
	public List<Scrip> findAll(){
		return Files.lines(getPath())
				.map(this::parse)
				.sorted().distinct()
				.collect(Collectors.toList());
	}
	
	private Scrip parse(String line){
		final String[] tokens = line.split(",");
		return Scrip.builder().isin(tokens[0]).code(tokens[1]).name(tokens[2]).build();
	}
	
	@SneakyThrows
	public void saveAll(Collection<Scrip> scrips) {
		Files.write(getPath(), scrips.stream()
				.map(this::format)
				.collect(Collectors.toSet()));
	}
	
	private String format(Scrip scrip){
		return String.join(",", scrip.getIsin(), scrip.getCode(), scrip.getName());
	}
	
	@SneakyThrows
	public ZonedDateTime getLastModifiedDate(){
		final Path path = getPath();
		if(!Files.exists(path)) return null;
		return ZonedDateTime.ofInstant(
				Files.getLastModifiedTime(getPath()).toInstant(), 
				ZoneId.systemDefault());
	}
	
}
