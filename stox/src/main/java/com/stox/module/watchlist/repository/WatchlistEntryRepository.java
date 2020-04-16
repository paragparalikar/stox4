package com.stox.module.watchlist.repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import com.stox.module.watchlist.model.WatchlistEntry;
import com.stox.persistence.CachingRepository;
import com.stox.util.JsonConverter;

import lombok.NonNull;

public class WatchlistEntryRepository extends CachingRepository<WatchlistEntry> {

	public WatchlistEntryRepository(final Path home, @NonNull final JsonConverter jsonConverter) {
		super(home.resolve(Paths.get("watchlist","entries")), WatchlistEntry.class, jsonConverter);
	}
	
	public void deleteByWatchlistId(final Integer watchlistId) {
		findAll().stream()
			.filter(entry -> Objects.equals(watchlistId, entry.getWatchlistId()))
			.map(WatchlistEntry::getId)
			.forEach(super::delete);
	}

}
