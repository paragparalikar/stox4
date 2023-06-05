package com.stox.data.downloader.scrip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import com.stox.common.scrip.Scrip;

public class NseScripMasterDownloader implements ScripMasterDownloader {
	private static final String URL_SCRIP_MASTER = "https://archives.nseindia.com/content/equities/EQUITY_L.csv";

	@Override
	public List<Scrip> download() throws IOException {
		final InputStream stream = new URL(URL_SCRIP_MASTER).openStream();
		final BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		return reader.lines().filter(this::isDataLine).map(this::parse).collect(Collectors.toList());
	}
	
	private boolean isDataLine(String line) {
		return !line.startsWith("SYMBOL");
	}

	private Scrip parse(String line) {
		final String[] tokens = line.split(",");
		return Scrip.builder()
				.isin(tokens[6])
				.code(tokens[0])
				.name(tokens[1])
				.build();
	}
	

}
