package com.stox.module.watchlist.repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import com.stox.module.watchlist.model.Watchlist;
import com.stox.persistence.CachingRepository;
import com.stox.util.JsonConverter;
import com.stox.util.Strings;

import lombok.NonNull;

public class WatchlistRepository extends CachingRepository<Watchlist> {

	public WatchlistRepository(final Path home, @NonNull final JsonConverter jsonConverter) {
		super(home.resolve(Paths.get("watchlist")), Watchlist.class, jsonConverter);
	}
	
	public boolean exists(final String name) {
		return findAll().stream()
			.map(Watchlist::getName)
			.map(String::trim)
			.anyMatch(Strings.equalsIgnoreCase(Optional.ofNullable(name)
					.map(String::trim)
					.orElse(null)));
	}

}
