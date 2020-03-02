package com.stox.module.charting.drawing;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.stox.persistence.store.JsonFileStore;
import com.stox.persistence.store.Store;
import com.stox.util.JsonConverter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class DrawingStateRepository {

	@NonNull
	private final Path home;
	private final JsonConverter jsonConverter;
	private final Type type = JsonConverter.type(HashSet.class, DrawingState.class);

	private Path path(final String isin) {
		return home.resolve(Paths.get("charting", "drawings", isin + ".json"));
	}

	@SneakyThrows
	public DrawingStateRepository persist(@NonNull final String isin, @NonNull final Set<DrawingState> drawings) {
		if (drawings.isEmpty()) {
			Files.deleteIfExists(path(isin));
		} else {
			new JsonFileStore<>(path(isin), Set.class, jsonConverter).write(drawings);
		}
		return this;
	}

	public Set<DrawingState> find(@NonNull final String isin) {
		final Store<Set<DrawingState>> store = new JsonFileStore<Set<DrawingState>>(path(isin), type, jsonConverter);
		return Optional.ofNullable(store.read()).orElse(Collections.emptySet());
	}

}
