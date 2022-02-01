package com.stox.client.kite.adapter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.stox.client.kite.KiteClient;
import com.stox.client.kite.model.Exchange;
import com.stox.client.kite.model.Instrument;
import com.stox.common.scrip.Scrip;

import lombok.SneakyThrows;

public class KiteScripService {
	
	private final List<Scrip> scrips;
	private final List<Instrument> instruments;
	private final Map<String, Scrip> isinScripMapping;
	private final Path path = Paths.get(System.getProperty("user.home"), ".stox4", "exchanges", "nse", "instruments.csv");
	
	@SneakyThrows
	public KiteScripService() {
		this.instruments = loadInstruments(path);
		this.scrips = instruments.stream().map(this::toScrip).collect(Collectors.toList());
		this.isinScripMapping = scrips.stream().collect(Collectors.toMap(Scrip::getIsin, Function.identity()));
	}
	
	protected List<Instrument> loadInstruments(Path path) throws Exception{
		List<Instrument> instruments = Collections.emptyList();
		if(Files.exists(path) 
				&& 0 < Files.size(path) 
				&& 0 < Files.getLastModifiedTime(path).compareTo(FileTime.from(Instant.now().minus(Duration.ofDays(1))))) {
			instruments = Files.lines(path).map(Instrument::new).collect(Collectors.toList());
		} else {
			instruments = KiteClient.getInstruments(Exchange.NSE);
			Files.write(path, instruments.stream().map(Instrument::toString).collect(Collectors.toList()));
		}
		return instruments;
	}
	
	protected Scrip toScrip(Instrument instrument) {
		return Scrip.builder()
				.isin(String.valueOf(instrument.getInstrumentToken()))
				.code(instrument.getTradingsymbol())
				.name(instrument.getName())
				.build();
	}
	
	public List<Scrip> findAllScrips() {
		return Collections.unmodifiableList(scrips);
	}

	public Scrip findScripByIsin(String isin) {
		return isinScripMapping.get(isin);
	}
	
}
