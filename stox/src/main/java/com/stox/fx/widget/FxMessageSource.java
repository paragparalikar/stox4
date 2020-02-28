package com.stox.fx.widget;

import java.util.HashMap;
import java.util.Map;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import lombok.NonNull;

public class FxMessageSource {

	private final Map<String, String> keyValueCache = new HashMap<>();
	private final HashMap<String, SimpleStringProperty> keyObservableValueCache = new HashMap<>();

	public ObservableValue<String> get(@NonNull final String key) {
		return get(key, key);
	}
	
	public ObservableValue<String> get(@NonNull final String key, final String defaultValue) {
		return keyObservableValueCache.computeIfAbsent(key, k -> new SimpleStringProperty(keyValueCache.getOrDefault(key, defaultValue)));
	}

}
