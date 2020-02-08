package com.stox.workbench.module;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import com.stox.persistence.store.SerializableFileStore;
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
		return path.resolve(Paths.get(String.join(".", "state", code, "ser")));
	}

	private <T extends ModuleViewState> Store<Set<T>> store(@NonNull final String code) {
		return new SerializableFileStore<Set<T>>(resolve(code));
	}

	@SneakyThrows
	public <T extends ModuleViewState> ModuleStateRepository write(@NonNull final String code, @NonNull final Set<T> states) {
		if (states.isEmpty()) {
			Files.deleteIfExists(resolve(code));
		} else {
			final Store<Set<T>> store = store(code);
			store.write(states);
		}
		return this;
	}

	public <T extends ModuleViewState> Set<T> read(@NonNull final String code) {
		final Store<Set<T>> store = store(code);
		return store.read();
	}

}
