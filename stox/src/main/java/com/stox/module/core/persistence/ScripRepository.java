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

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

@RequiredArgsConstructor
public class ScripRepository {

	@NonNull
	private final Path home;
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
		final Scrip scrip = new Scrip();
		scrip.setExchange(exchange);
		scrip.setIsin(tokens[0]);
		scrip.setCode(tokens[1]);
		scrip.setName(tokens[2]);
		return scrip;
	}
	
	private String format(Scrip scrip){
		return String.join(",", scrip.getIsin(), scrip.getCode(), scrip.getName());
	}
	
	private Path getPath(Exchange exchange){
		return home.resolve(Paths.get("exchanges", exchange.getCode().toLowerCase(), "scrips.csv"));
	}

	@SneakyThrows
	public synchronized void save(Exchange exchange, List<Scrip> scrips) {
		final List<String> lines = scrips.stream().sorted().map(this::format).collect(Collectors.toList());
		Files.write(getPath(exchange), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
		cache(exchange, scrips);
	}
	
	@SneakyThrows
	public Date getLastModifiedDate(Exchange exchange){
		return new Date(Files.getLastModifiedTime(getPath(exchange)).toMillis());
	}
	
	private synchronized void cache(Exchange exchange, List<Scrip> scrips){
		exchangeScripMapping.put(exchange, scrips);
		isinScripMapping.putAll(scrips.stream().collect(Collectors.toMap(Scrip::getIsin, Function.identity())));
		exchangeCodeScripMapping.put(exchange, scrips.stream().collect(Collectors.toMap(Scrip::getCode, Function.identity())));
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
