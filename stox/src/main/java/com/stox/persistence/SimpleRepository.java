package com.stox.persistence;

import java.nio.file.Path;

import com.stox.Context;
import com.stox.core.model.intf.HasId;
import com.stox.util.JsonConverter;

import lombok.NonNull;

public class SimpleRepository<T extends HasId<Integer>> extends JsonFileRepository<Integer, T> {

	public SimpleRepository(final Context context, @NonNull final String name) {
		this(context.getConfig().getHome(), name, context.getJsonConverter());
	}
	
	public SimpleRepository(@NonNull final Path home, @NonNull final String name, @NonNull final JsonConverter jsonConverter) {
		super(home, name, 
				HasId::getId, 
				(id, entity) -> entity.setId(id), 
				IntegerFileSequence.builder()
					.home(home)
					.name(name)
					.build(), 
				jsonConverter);
	}

}
