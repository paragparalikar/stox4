package com.stox.module.core.persistence;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.stox.module.core.model.Exchange;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.model.event.ScripsChangedEvent;
import com.stox.util.EventBus;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class ScripRepository {

	@NonNull
	private final Path home;
	@NonNull
	private final EventBus eventBus;
	private final Map<String, Scrip> isinScripMapping = new HashMap<>();
	private final Map<Exchange, List<Scrip>> exchangeScripMapping = new HashMap<>();
	private final Map<Exchange, Map<String, Scrip>> exchangeCodeScripMapping = new HashMap<>();

	public synchronized void init() {
		Arrays.asList(Exchange.values()).forEach(this::load);
	}
	
	@SneakyThrows
	private synchronized void load(Exchange exchange){
		final Path path = getPath(exchange);
		if(Files.exists(path)) {
			final List<Scrip> scrips = Files.readAllLines(path).stream().map(line -> parse(exchange, line)).collect(Collectors.toList());
			cache(exchange, scrips);
		}
	}
	
	private Scrip parse(Exchange exchange, String line){
		final String[] tokens = line.split(",");
		return Scrip.of(tokens[0], tokens[1], tokens[2], exchange);
	}
	
	private String format(Scrip scrip){
		return String.join(",", scrip.isin(), scrip.code(), scrip.name());
	}
	
	private Path getPath(Exchange exchange){
		return home.resolve(Paths.get("exchanges", exchange.code().toLowerCase(), "scrips.csv"));
	}

	@SneakyThrows
	public synchronized void save(Exchange exchange, List<Scrip> scrips) {
		final Path path = getPath(exchange);
		if(!Files.exists(path)) {
			Files.createDirectories(path.getParent());
		}
		final List<String> lines = scrips.stream().sorted().map(this::format).collect(Collectors.toList());
		Files.write(path, lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
		cache(exchange, scrips);
	}
	
	@SneakyThrows
	public Date getLastModifiedDate(Exchange exchange){
		final Path path = getPath(exchange);
		return Files.exists(path) ? new Date(Files.getLastModifiedTime(path).toMillis()) : null;
	}
	
	private synchronized void cache(Exchange exchange, List<Scrip> scrips){
		exchangeScripMapping.put(exchange, scrips);
		isinScripMapping.putAll(scrips.stream().distinct().collect(Collectors.toMap(Scrip::isin, Function.identity())));
		exchangeCodeScripMapping.put(exchange, scrips.stream().distinct().collect(Collectors.toMap(Scrip::code, Function.identity())));
		eventBus.fire(new ScripsChangedEvent(exchange, scrips));
	}

	public List<Scrip> find(Exchange exchange) {
		return Optional.ofNullable(exchangeScripMapping.get(exchange)).map(Collections::unmodifiableList).orElse(Collections.emptyList());
	}

	public Scrip find(String isin) {
		return isinScripMapping.get(isin);
	}
	
	public Scrip find(Exchange exchange, String code){
		return Optional.ofNullable(exchangeCodeScripMapping.get(exchange)).orElse(Collections.emptyMap()).get(code);
	}

}
