package com.stox.module.core.persistence;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import com.stox.module.core.model.Exchange;
import com.stox.persistence.store.DateFileStore;
import com.stox.persistence.store.Store;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExchangeRepository {

	@NonNull
	private final Path path;
	
	private Path resolve(@NonNull final Exchange exchange) {
		return path.resolve(Paths.get("exchanges", exchange.getCode().toLowerCase(),"last-download-date.txt"));
	}
	
	private Store<Date> store(@NonNull final Path path){
		return new DateFileStore(path);
	}
	
	public Date readLastDownloadDate(@NonNull final Exchange exchange) {
		return store(resolve(exchange)).read();
	}
	
	public ExchangeRepository writeLastDownloadDate(@NonNull final Exchange exchange, @NonNull final Date date) {
		store(resolve(exchange)).write(date);
		return this;
	}

}
