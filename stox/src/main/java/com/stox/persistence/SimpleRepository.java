package com.stox.persistence;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.stox.module.core.model.intf.HasId;

import lombok.NonNull;

public class SimpleRepository<T extends HasId<Integer>> extends JsonFileRepository<Integer, T> {
	
	public SimpleRepository(final Path path, @NonNull final Class<T> type, @NonNull final Gson gson) {
		super(path, type, gson, HasId::getId, (id, entity) -> entity.setId(id), new IntegerFileSequence(path.resolve(Paths.get("sequence.txt"))));
	}

}
