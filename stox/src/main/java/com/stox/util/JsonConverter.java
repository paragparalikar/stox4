package com.stox.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.NonNull;

public class JsonConverter {

	private final Gson gson = new GsonBuilder()
			.enableComplexMapKeySerialization()
			.serializeNulls()
			.setPrettyPrinting()
			.create();

	public String toJson(@NonNull final Object object) {
		return gson.toJson(object);
	}
	
	public <T> T fromJson(@NonNull final String json, @NonNull final Class<T> type) {
		return gson.fromJson(json, type);
	}
	
}
