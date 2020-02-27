package com.stox.module.charting.drawing;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.stox.persistence.store.JsonFileStore;
import com.stox.util.JsonConverter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DrawingRepository {

	@NonNull
	private final Path home;
	
	@NonNull
	private final JsonConverter jsonConverter;
	
	private Path path(final String isin) {
		return home.resolve(Paths.get("chart", "drawings", isin));
	}
	
	public DrawingRepository persist(@NonNull final String isin, @NonNull final Set<Drawing> drawings) {
		new JsonFileStore<>(path(isin), Set.class, jsonConverter).write(drawings);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Set<Drawing> find(@NonNull final String isin){
		return (Set<Drawing>) new JsonFileStore<>(path(isin), JsonConverter.type(HashSet.class, Drawing.class), jsonConverter).read();
	}
	
}
