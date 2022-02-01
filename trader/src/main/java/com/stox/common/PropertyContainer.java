package com.stox.common;

import java.util.HashMap;
import java.util.Map;

public class PropertyContainer {

	private final Map<Object, Object> container = new HashMap<>();

	public Object put(Object key, Object value) {
		return container.put(key, value);
	}

	public Object get(Object key) {
		return container.get(key);
	}

	public <T> T get(Object key, Class<T> type) {
		final Object value = get(key);
		return null == value ? null : type.cast(value);
	}

}
