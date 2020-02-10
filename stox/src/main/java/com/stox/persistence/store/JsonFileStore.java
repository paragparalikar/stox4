package com.stox.persistence.store;

import java.lang.reflect.Type;
import java.nio.file.Path;

import com.google.gson.Gson;
import com.stox.util.StringUtil;

import lombok.NonNull;

public class JsonFileStore<T> implements Store<T> {

	private final Type type;
	
	private final Class<T> clazz;
	
	private final TextFileStore textFileStore;
	
	private final Gson gson;
	
	public JsonFileStore(@NonNull final Path path,@NonNull final Type type,@NonNull final Gson gson) {
		this.type = type;
		this.clazz = null;
		this.gson = gson;
		this.textFileStore = new TextFileStore(path);
	}
	
	public JsonFileStore(@NonNull final Path path,@NonNull final Class<T> clazz,@NonNull final Gson gson) {
		this.type = null;
		this.clazz = clazz;
		this.gson = gson;
		this.textFileStore = new TextFileStore(path);
	}

	@Override
	public T read() {
		final String json = textFileStore.read();
		return StringUtil.hasText(json) ? gson.fromJson(json, null == type ? clazz : type) : null;
	}

	@Override
	public T write(T data) {
		textFileStore.write(gson.toJson(data));
		return data;
	}

}
