package com.stox.common.scrip;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import lombok.SneakyThrows;

public class ScripRepository {

	private final Path path = Paths.get(System.getProperty("user.home"), ".stox4", "exchanges", "nse", "scrips.csv");
	
	@SneakyThrows
	public Collection<Scrip> findAll(){
		return Files.lines(path).map(this::parse).collect(Collectors.toSet());
	}
	
	private Scrip parse(String line){
		final String[] tokens = line.split(",");
		return Scrip.builder().isin(tokens[0]).code(tokens[1]).name(tokens[2]).build();
	}
	
	@SneakyThrows
	public void saveAll(Collection<Scrip> scrips) {
		Files.write(path, scrips.stream()
				.map(this::format)
				.collect(Collectors.toSet()));
	}
	
	private String format(Scrip scrip){
		return String.join(",", scrip.getIsin(), scrip.getCode(), scrip.getName());
	}
	
	@SneakyThrows
	public Date getLastModifiedDate(){
		return Files.exists(path) ? new Date(Files.getLastModifiedTime(path).toMillis()) : null;
	}
	
}
