package com.stox.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import lombok.NonNull;

public class JsonConverter {

	private final Gson gson; 
	
	public JsonConverter() {
		this(new GsonBuilder());
	}
	
	public JsonConverter(final GsonBuilder gsonBuilder) {
		this(gsonBuilder
				.enableComplexMapKeySerialization()
				.serializeNulls()
				.setPrettyPrinting()
				.create());
	}
	
	public JsonConverter(@NonNull final Gson gson) {
		this.gson = gson;
	}
	
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
	
	public static Type type(@NonNull final Type raw, @NonNull final Type... parameters) {
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
