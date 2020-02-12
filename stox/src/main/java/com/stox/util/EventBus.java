package com.stox.util;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import lombok.NonNull;

public class EventBus {

	@SuppressWarnings("rawtypes")
	private final Map<Class<?>, Set<Consumer>> cache = new HashMap<>();

	public <T> void subscribe(@NonNull final Class<T> type, @NonNull final Consumer<T> handler) {
		cache.computeIfAbsent(type, t -> Collections.newSetFromMap(new WeakHashMap<>())).add(handler);
	}

	public void unsubscribe(@NonNull final Class<?> type, @NonNull final Consumer<?> handler) {
		cache.getOrDefault(type, Collections.emptySet()).remove(handler);
	}

	@SuppressWarnings("unchecked")
	public void fire(@NonNull final Object event) {
		clean();
		cache.keySet().stream().filter(event.getClass()::isAssignableFrom).forEach(type -> {
			cache.getOrDefault(type, Collections.emptySet()).forEach(handler -> {
				try {
					handler.accept(event);
				} catch (Exception e) {
					e.printStackTrace();
				}
			});
		});
	}

	private void clean() {
		final Set<Class<?>> keys = cache.entrySet().stream().filter(entry -> entry.getValue().isEmpty()).map(Entry::getKey).collect(Collectors.toSet());
		keys.forEach(cache::remove);
	}

}
