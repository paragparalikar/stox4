package com.stox.workbench;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.stox.persistence.store.JsonFileStore;
import com.stox.util.JsonConverter;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WorkbenchStateRepository {

	@NonNull
	private final Path home;
	
	@NonNull
	private final JsonConverter jsonConverter;
	
	private Path path() {
		return home.resolve(Paths.get("workbench","state.workbench.json"));
	}
	
	public WorkbenchState read() {
		return new JsonFileStore<WorkbenchState>(path(), WorkbenchState.class, jsonConverter).read();
	}

	public void write(@NonNull final WorkbenchState state) {
		new JsonFileStore<WorkbenchState>(path(), WorkbenchState.class, jsonConverter).write(state);
	}
	
}
