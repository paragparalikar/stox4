package com.stox.fx.widget;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import com.stox.util.WeakKeyValueMap;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public class FxMessageSource {

	private final Map<String, String> keyValueCache = new HashMap<>();
	private final WeakKeyValueMap<String, SimpleStringProperty> keyObservableValueCache = new WeakKeyValueMap<>();

	public ObservableValue<String> get(@NonNull final String key) {
		return get(key, key);
	}
	
	public ObservableValue<String> get(@NonNull final String key, final String defaultValue) {
		return keyObservableValueCache.computeIfAbsent(key, k -> {
			return new WeakReference<SimpleStringProperty>(
					new SimpleStringProperty(keyValueCache.getOrDefault(key, defaultValue)));
		}).get();
	}

}
