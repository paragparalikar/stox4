package com.stox.persistence.store;

import java.nio.file.Path;

import com.stox.util.JsonConverter;

import lombok.NonNull;

public class JsonFileStore<T> implements Store<T> {

	private final Class<T> type;
	
	private final TextFileStore textFileStore;
	
	private final JsonConverter jsonConverter;
	
	public JsonFileStore(@NonNull final Path path,@NonNull final Class<T> type,@NonNull final JsonConverter jsonConverter) {
		this.type = type;
		this.jsonConverter = jsonConverter;
		this.textFileStore = new TextFileStore(path);
	}

	@Override
	public T read() {
		return jsonConverter.fromJson(textFileStore.read(), type);
	}

	@Override
	public T write(T data) {
		textFileStore.write(jsonConverter.toJson(data));
		return data;
	}

}
