package com.stox.watchlist;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class WatchlistRepository {

	private final Path home;
	
	private Path getPath() {
		return home.resolve("watchlists");
	}
	
	private Path getPath(String name) {
		return getPath().resolve(name);
	}

	@SneakyThrows
	public void create(Watchlist watchlist) {
		final String name = watchlist.getName();
		if(existsByName(name)) throw new IllegalArgumentException(name + " already exists");
		final Path path = getPath(name);
		Files.createDirectories(path.getParent());
		Files.createFile(path);
	}
	
	@SneakyThrows
	public List<Watchlist> findAll() {
		final Path path = getPath();
		return Files.exists(path) ?  
				Files.list(path).map(this::read).collect(Collectors.toList())
				: Collections.emptyList();
	}
	
	public Watchlist findByName(String name) {
		if(!existsByName(name)) throw new IllegalArgumentException(name + " does not exists");
		return read(getPath(name));
	}
	
	@SneakyThrows
	private Watchlist read(Path path) {
		final Watchlist watchlist = new Watchlist();
		watchlist.setName(path.getFileName().toString());
		watchlist.setEntries(Files.lines(path).distinct().collect(Collectors.toList()));
		return watchlist;
	}	
	
	@SneakyThrows
	public void rename(String oldName, String newName) {
		final Path newPath = getPath(newName);
		if(existsByName(newName)) throw new IllegalArgumentException(newName + " already exists");
		final Path oldPath = getPath(oldName);
		if(!existsByName(oldName)) throw new IllegalArgumentException(oldName + " does not exists");
		Files.move(oldPath, newPath);
	}
	
	public boolean existsByName(String name) {
		return Files.exists(getPath(name));
	}
	
	@SneakyThrows
	public void update(Watchlist watchlist) {
		if(!existsByName(watchlist.getName())) throw new IllegalArgumentException(watchlist.getName() + " does not exists");
		final Path path = getPath(watchlist.getName());
		Files.createDirectories(path.getParent());
		watchlist.setEntries(watchlist.getEntries().stream().distinct().collect(Collectors.toList()));
		Files.write(path, watchlist.getEntries(), 
				StandardOpenOption.CREATE, 
				StandardOpenOption.WRITE, 
				StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	@SneakyThrows
	public Watchlist delete(String name) {
		final Watchlist watchlist = findByName(name);
		Files.deleteIfExists(getPath(name));
		return watchlist;
	}
	
	@SneakyThrows
	public Watchlist truncate(String name) {
		final Watchlist watchlist = findByName(name);
		Files.write(getPath(name), Collections.emptyList(), 
				StandardOpenOption.WRITE, 
				StandardOpenOption.TRUNCATE_EXISTING);
		return watchlist;
	}
}
