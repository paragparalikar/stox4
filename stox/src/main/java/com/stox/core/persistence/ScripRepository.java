package com.stox.core.persistence;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.stox.core.model.Exchange;
import com.stox.core.model.Scrip;
import com.stox.util.FileUtil;

import lombok.NonNull;
import lombok.SneakyThrows;

public class ScripRepository {

	private final Path home;
	private final Map<String, Scrip> isinScripMapping = new HashMap<>();
	private final Map<Exchange, List<Scrip>> exchangeScripMapping = new HashMap<>();
	private final Map<Exchange, Map<String, Scrip>> exchangeCodeScripMapping = new HashMap<>();

	private ScripRepository(@NonNull final Path home) {
		this.home = home;
		load();
	}

	private synchronized void load() {
		for (Exchange exchange : Exchange.values()) {
			load(exchange);
		}
	}
	
	@SneakyThrows
	private synchronized void load(Exchange exchange){
		final File file = new File(getPath(exchange));
		if(file.exists()){
			final List<Scrip> scrips = Files.newBufferedReader(file.toPath())
					.lines().map(line -> parse(exchange, line)).collect(Collectors.toList());
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
	
	private String getPath(Exchange exchange){
		return home.resolve(Paths.get(exchange.getCode().toLowerCase(), "scrips.csv")).toString();
	}

	@SneakyThrows
	public synchronized void save(Exchange exchange, List<Scrip> scrips) {
		final List<String> lines = scrips.stream().sorted().map(this::format).collect(Collectors.toList());
		Files.write(FileUtil.safeGet(getPath(exchange)), lines, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE);
		cache(exchange, scrips);
	}
	
	public Date getLastModifiedDate(Exchange exchange){
		return new Date(new File(getPath(exchange)).lastModified());
	}
	
	private synchronized void cache(Exchange exchange, List<Scrip> scrips){
		final HashMap<String, Scrip> codeScripMapping = new HashMap<>();
		scrips.forEach(scrip -> {
			codeScripMapping.put(scrip.getCode(), scrip);
			isinScripMapping.put(scrip.getIsin(), scrip);
		});
		exchangeScripMapping.put(exchange, scrips);
		exchangeCodeScripMapping.put(exchange, codeScripMapping);
	}

	public List<Scrip> find(Exchange exchange) {
		final List<Scrip> scrips = exchangeScripMapping.get(exchange);
		return null == scrips ? Collections.emptyList() : Collections.unmodifiableList(scrips);
	}

	public Scrip find(String isin) {
		return isinScripMapping.get(isin);
	}
	
	public Scrip find(Exchange exchange, String code){
		final Map<String, Scrip> codeScripMapping = exchangeCodeScripMapping.get(exchange);
		return null == codeScripMapping ? null : codeScripMapping.get(code);
	}

}
