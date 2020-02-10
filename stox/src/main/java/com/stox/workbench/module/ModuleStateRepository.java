package com.stox.workbench.module;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stox.persistence.store.JsonFileStore;
import com.stox.persistence.store.Store;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class ModuleStateRepository {

	@NonNull
	private final Gson gson;
	
	@NonNull
	private final Path path;
	
	private Path resolve(@NonNull final String code) {
		return path.resolve(Paths.get(code, "state.json"));
	}
	
	private Store<Set<ModuleViewState>> store(@NonNull final Path path){
		return new JsonFileStore<>(path, new TypeToken<Set<ModuleViewState>>(){}.getType(), gson);
	}

	@SneakyThrows
	public ModuleStateRepository write(@NonNull final String code, @NonNull final Set<ModuleViewState> states) {
		store(resolve(code)).write(states);
		return this;
	}

	public Set<ModuleViewState> read(@NonNull final String code) {
		return store(resolve(code)).read();
	}

}
