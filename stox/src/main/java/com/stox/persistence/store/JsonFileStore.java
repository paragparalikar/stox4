package com.stox.persistence.store;

import java.lang.reflect.Type;
import java.nio.file.Path;

import com.stox.util.JsonConverter;
import com.stox.util.Strings;

import lombok.NonNull;

public class JsonFileStore<T> implements Store<T> {

	private final Type type;
	
	private final Class<T> clazz;
	
	private final TextFileStore textFileStore;
	
	private final JsonConverter jsonConverter;
	
	public JsonFileStore(@NonNull final Path path,@NonNull final Type type,@NonNull final JsonConverter jsonConverter) {
		this.type = type;
		this.clazz = null;
		this.jsonConverter = jsonConverter;
		this.textFileStore = new TextFileStore(path);
	}
	
	public JsonFileStore(@NonNull final Path path,@NonNull final Class<T> clazz,@NonNull final JsonConverter jsonConverter) {
		this.type = null;
		this.clazz = clazz;
		this.jsonConverter = jsonConverter;
		this.textFileStore = new TextFileStore(path);
	}

	@Override
	public T read() {
		final String json = textFileStore.read();
		return Strings.hasText(json) ? jsonConverter.fromJson(json, null == type ? clazz : type) : null;
	}

	@Override
	public T write(T data) {
		textFileStore.write(jsonConverter.toJson(data));
		return data;
	}

}
