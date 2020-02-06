package com.stox.util;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.NonNull;

public class JsonConverter {

	private final Gson gson = new GsonBuilder()
			.enableComplexMapKeySerialization()
			.serializeNulls()
			.setPrettyPrinting()
			.create();

	public JsonConverter write(@NonNull final Object object, @NonNull final OutputStream outputStream) {
		gson.toJson(object, new OutputStreamWriter(outputStream));
		return this;
	}
	
	public Object read(@NonNull final InputStream inputStream) {
		return gson.fromJson(new InputStreamReader(inputStream), Object.class);
	}
	
	public <T> T read(@NonNull final InputStream inputStream, @NonNull final Class<T> type) {
		return gson.fromJson(new InputStreamReader(inputStream), type);
	}
	
}
