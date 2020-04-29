package com.stox.module.core.model;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import com.stox.util.Strings;

import lombok.Data;

@Data
public class Scrip implements Comparable<Scrip> {

	private static final Map<Exchange, Map<String, Scrip>> CACHE = new EnumMap<>(Exchange.class);

	public static Scrip of(final String isin, final String code, final String name, final Exchange exchange) {
		return CACHE
				.computeIfAbsent(exchange, key -> new HashMap<>())
				.computeIfAbsent(isin, i -> new Scrip(isin, code, name, exchange));
	}

	private String isin;

	private String code;

	private String name;

	private Exchange exchange;

	protected Scrip(final String isin, final String code, final String name, final Exchange exchange) {
		Strings.requireText(isin);
		Strings.requireText(name);
		Strings.requireText(code);
		Objects.requireNonNull(exchange);
		this.isin = isin;
		this.code = code;
		this.name = name;
		this.exchange = exchange;
	}

	@Override
	public int compareTo(Scrip scrip) {
		return Objects.compare(name, scrip.name, (one, two) -> one.compareToIgnoreCase(two));
	}

	public String toString() {
		return name;
	}

}
