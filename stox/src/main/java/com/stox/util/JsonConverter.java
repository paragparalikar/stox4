package com.stox.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

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
	
	public String toJson(@NonNull final Object object, @NonNull final Type type) {
		return gson.toJson(object, type);
	}
	
	public <T> T fromJson(@NonNull final String json, @NonNull final Class<T> type) {
		return gson.fromJson(json, type);
	}
	
	public <T> T fromJson(@NonNull final String json, @NonNull final Type type) {
		return gson.fromJson(json, type);
	}
	
	public Type type(@NonNull final Type raw, @NonNull final Type... parameters) {
		return new ParameterizedType() {

			@Override
			public Type[] getActualTypeArguments() {
				return parameters;
			}

			@Override
			public Type getRawType() {
				return raw;
			}

			@Override
			public Type getOwnerType() {
				return null;
			}
			
		};
	}
}
