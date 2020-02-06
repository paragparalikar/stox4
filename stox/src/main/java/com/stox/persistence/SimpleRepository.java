package com.stox.persistence;

import java.nio.file.Path;
import java.nio.file.Paths;

import com.stox.module.core.model.intf.HasId;
import com.stox.util.JsonConverter;

import lombok.NonNull;

public class SimpleRepository<T extends HasId<Integer>> extends JsonFileRepository<Integer, T> {
	
	public SimpleRepository(final Path path, @NonNull final Class<T> type, @NonNull final JsonConverter jsonConverter) {
		super(path, type, jsonConverter, HasId::getId, (id, entity) -> entity.setId(id), new IntegerFileSequence(path.resolve(Paths.get("sequence.txt"))));
	}

}
