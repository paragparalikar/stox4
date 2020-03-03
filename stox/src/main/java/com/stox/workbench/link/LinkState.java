package com.stox.workbench.link;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class LinkState{
	
	public static LinkState.Builder builder() {
		return new Builder();
	}
	
	public static class Builder{
		
		private final Map<String, String> properties = new HashMap<>();
		
		public LinkState.Builder put(final String key, final String value) {
			properties.put(key, value);
			return this;
		}
		
		public LinkState.Builder putAll(final Map<String, String> properties) {
			this.properties.putAll(properties);
			return this;
		}
		
		public LinkState build() {
			return new LinkState(properties);
		}
		
	}
	
	private final Map<String, String> properties;
	
	public LinkState(final Map<String, String> properties) {
		this.properties = Collections.unmodifiableMap(new HashMap<>(Optional.ofNullable(properties).orElse(Collections.emptyMap())));
	}
	
	public String get(final String key) {
		return properties.get(key);
	}
			
	public String getOrDefault(final String key, final String defaultValue) {
		return properties.getOrDefault(key, defaultValue);
	}
	
	public Long getLong(final String key) {
		final String value = properties.get(key);
		return null == value ? null : Long.parseLong(value);
	}
	
	public Long getLong(final String key, final Long defaultValue) {
		final String value = properties.get(key);
		return null == value ? defaultValue : Long.parseLong(value);
	}
	
	public <T> T get(final String key, final Function<String,T> function) {
		final String value = properties.get(key);
		return function.apply(value);
	}
	
	public <T> T get(final String key, final T defaultValue, final Function<String,T> function) {
		final String value = properties.get(key);
		return null == value ? defaultValue : function.apply(value);
	}
	
}