package com.stox.watchlist;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class WatchlistFileRepository {

	private final Path home;
	
	private Path getPath() {
		return home.resolve("watchlists");
	}
	
	private Path getPath(String name) {
		return getPath().resolve(name);
	}

	@SneakyThrows
	public List<Watchlist> findAll() {
		return Files.list(getPath()).map(this::read).collect(Collectors.toList());
	}
	
	public Watchlist findByName(String name) {
		return read(getPath(name));
	}
	
	@SneakyThrows
	private Watchlist read(Path path) {
		final Watchlist watchlist = new Watchlist();
		watchlist.setName(path.getFileName().toString());
		watchlist.setEntries(Files.readAllLines(path));
		return watchlist;
	}	
	
	@SneakyThrows
	public void save(Watchlist watchlist) {
		final Path path = getPath(watchlist.getName());
		Files.createDirectories(path.getParent());
		Files.write(path, watchlist.getEntries(), StandardOpenOption.CREATE, 
				StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	@SneakyThrows
	public void append(String name, String entry) {
		final Path path = getPath(name);
		Files.createDirectories(path.getParent());
		Files.write(path, Arrays.asList(entry), StandardOpenOption.CREATE, 
				StandardOpenOption.WRITE, StandardOpenOption.APPEND);
	}
	
	@SneakyThrows
	public void delete(String name) {
		Files.deleteIfExists(getPath(name));
	}
	
	@SneakyThrows
	public void truncate(String name) {
		final Path path = getPath(name);
		if(Files.exists(path)) {
			Files.write(path, Collections.emptyList(), StandardOpenOption.WRITE, 
					StandardOpenOption.TRUNCATE_EXISTING);
		}
	}
}
