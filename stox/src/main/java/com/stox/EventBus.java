package com.stox;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.WeakHashMap;

import com.stox.util.Uncheck;

import lombok.NonNull;

public class EventBus {

	private static final Map<EventHandler, Subscription> subscriptions = new WeakHashMap<>();
	private final Map<String, List<WeakReference<EventHandler>>> cache = new HashMap<>();

	public void publish(@NonNull final String topic, @NonNull final Object payload) {
		cache.getOrDefault(topic, Collections.emptyList()).stream().map(Reference::get).filter(Objects::nonNull).forEach(Uncheck.consumer(handler -> handler.handle(payload)));
	}

	public <T> Subscription subscribe(@NonNull final String topic, @NonNull final EventHandler<T> handler) {
		return new Subscription(topic, handler, cache);
	}

	public <T> void unsubscribe(@NonNull final Subscription subscription) {
		subscription.clear();
	}

	@FunctionalInterface
	public static interface EventHandler<T> {

		void handle(T payload) throws Exception;

		default void finalize() throws Throwable {
			Optional.ofNullable(subscriptions.get(this)).ifPresent(Subscription::clear);
		}

	}

	public static class Subscription {

		private final String topic;
		private final EventHandler handler;
		private final WeakReference<EventHandler> reference;
		private final List<WeakReference<EventHandler>> parent;
		private final Map<String, List<WeakReference<EventHandler>>> cache;

		Subscription(final String topic, final EventHandler handler, final Map<String, List<WeakReference<EventHandler>>> cache) {
			this.topic = topic;
			this.cache = cache;
			this.handler = handler;
			this.reference = new WeakReference<>(handler);
			this.parent = cache.computeIfAbsent(topic, t -> new LinkedList<>());
			parent.add(reference);
			subscriptions.put(handler, this);
		}

		void clear() {
			reference.clear();
			subscriptions.remove(handler);
			parent.remove(reference);
			if (parent.isEmpty()) {
				cache.remove(topic);
			}
		}

	}

}
