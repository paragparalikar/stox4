package com.stox.core.persistence;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import com.stox.core.model.Exchange;
import com.stox.persistence.store.DateFileStore;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExchangeRepository {

	@NonNull
	private final Path path;
	
	public Date getLastDownloadDate(@NonNull final Exchange exchange) {
		final Path filePath = path.resolve(Paths.get("exchanges", exchange.getCode().toLowerCase(),"last-download-date.txt"));
		return new DateFileStore(filePath).read();
	}

}
