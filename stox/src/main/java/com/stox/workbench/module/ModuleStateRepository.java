package com.stox.workbench.module;

import java.lang.reflect.Type;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

import com.stox.persistence.store.JsonFileStore;
import com.stox.persistence.store.Store;
import com.stox.util.JsonConverter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class ModuleStateRepository {

	@NonNull
	private final Path path;

	@NonNull
	private final JsonConverter jsonConverter;

	private Path resolve(@NonNull final String code) {
		return path.resolve(Paths.get(code, "state.json"));
	}

	private <T extends ModuleViewState> Store<Set<T>> store(@NonNull final Path path, @NonNull final Type stateType) {
		return new JsonFileStore<>(path, JsonConverter.type(HashSet.class, stateType), jsonConverter);
	}

	@SneakyThrows
	public <T extends ModuleViewState> ModuleStateRepository write(@NonNull final String code, @NonNull final Set<T> states) {
		final Type type = states.isEmpty() ? Object.class : states.iterator().next().getClass();
		final Store<Set<T>> store = store(resolve(code), type);
		store.write(states);
		return this;
	}

	public <T extends ModuleViewState> Set<T> read(@NonNull final String code, @NonNull final Type stateType) {
		final Store<Set<T>> store = store(resolve(code), stateType);
		return store.read();
	}

}
