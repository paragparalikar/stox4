package com.stox.data.exporter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.format.DateTimeFormatter;

import org.ta4j.core.Bar;

import com.stox.common.bar.BarRepository;
import com.stox.common.scrip.Scrip;
import com.stox.common.scrip.ScripRepository;

public class BarDataExporter {

	private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
	private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hhmmss");
	
	public static void main(String[] args) throws IOException {
		final Path basePath = Paths.get(System.getProperty("user.home"), "data");
		final Path home = Paths.get(System.getProperty("user.home"), ".stox4j");
		final BarRepository barRepository = new BarRepository(home);
		final ScripRepository scripRepository = new ScripRepository(home);
		Files.createDirectories(basePath);
		for(Scrip scrip : scripRepository.findAll()) {
			final Path path = basePath.resolve(scrip.getIsin() + ".txt");
			final BufferedWriter writer = Files.newBufferedWriter(path);
			for(Bar bar : barRepository.find(scrip.getIsin(), Integer.MAX_VALUE)) {
				writer.write(toCsv(scrip, bar));
				writer.newLine();
			}
			System.out.println("Exported " + scrip.getCode());
		}
	}
	
	private static String toCsv(Scrip scrip, Bar bar) {
		final String date = dateFormatter.format(bar.getEndTime());
		final String time = timeFormatter.format(bar.getEndTime());
		return String.join(",", scrip.getIsin(), scrip.getCode(), scrip.getName(), date, time,
				String.valueOf(bar.getOpenPrice().doubleValue()),
				String.valueOf(bar.getHighPrice().doubleValue()),
				String.valueOf(bar.getLowPrice().doubleValue()),
				String.valueOf(bar.getClosePrice().doubleValue()),
				String.valueOf(bar.getVolume().longValue()));
	}
	
}
