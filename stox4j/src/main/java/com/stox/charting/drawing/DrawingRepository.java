package com.stox.charting.drawing;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import com.stox.charting.chart.Chart;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class DrawingRepository {
	
	private final Path home;
	
	private Path getPath(String isin) {
		return home.resolve(Paths.get("charts", "drawings", isin));
	}
	
	@SneakyThrows
	@SuppressWarnings("unchecked")
	public Set<Drawing<?>> findByIsin(String isin, Chart chart){
		final Path path = getPath(isin);
		final Set<DrawingState> states = Files.exists(path) ? 
				(Set<DrawingState>) new ObjectInputStream(Files.newInputStream(path)).readObject() : 
				Collections.emptySet();
		 return states.stream().map(state -> state.create(chart)).collect(Collectors.toSet());
	}

	@SneakyThrows
	public void save(String isin, Set<Drawing<?>> drawings) {
		final Path path = getPath(isin);
		Files.createDirectories(path.getParent());
		try(final ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(path))){
			final Set<DrawingState> states = drawings.stream()
					.map(Drawing::getState).collect(Collectors.toSet());
			oos.writeObject(states);
		}
	}
}
