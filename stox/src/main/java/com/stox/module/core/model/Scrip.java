package com.stox.module.core.model;

import java.io.Serializable;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import lombok.experimental.Accessors;

@Value
@RequiredArgsConstructor
@Accessors(fluent = true)
public class Scrip implements Comparable<Scrip>, Serializable {
	private static final long serialVersionUID = 1L;

	private static final Map<Exchange, Map<String, Scrip>> CACHE = new EnumMap<>(Exchange.class);

	public static Scrip of(final String isin, final String code, final String name, final Exchange exchange) {
		return CACHE
				.computeIfAbsent(exchange, key -> new HashMap<>())
				.computeIfAbsent(isin, i -> new Scrip(isin, code, name, exchange));
	}

	@NonNull
	private final String isin;

	@NonNull
	private final String code;

	@NonNull
	private final String name;

	@NonNull
	private final Exchange exchange;

	@Override
	public int compareTo(Scrip scrip) {
		return Objects.compare(name, scrip.name, (one, two) -> one.compareToIgnoreCase(two));
	}

	public String toString() {
		return name;
	}

}
