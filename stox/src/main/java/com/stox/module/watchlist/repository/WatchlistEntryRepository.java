package com.stox.module.watchlist.repository;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import com.stox.module.core.model.BarSpan;
import com.stox.module.watchlist.model.WatchlistEntry;
import com.stox.persistence.CachingRepository;
import com.stox.util.JsonConverter;

import lombok.NonNull;

public class WatchlistEntryRepository extends CachingRepository<WatchlistEntry> {

	public WatchlistEntryRepository(final Path home, @NonNull final JsonConverter jsonConverter) {
		super(home.resolve(Paths.get("watchlist","entries")), WatchlistEntry.class, jsonConverter);
	}
	
	public boolean existsByWatchlistId(@NonNull final String isin,@NonNull final BarSpan barSpan,@NonNull final Integer watchlistId){
		return findByWatchlistId(watchlistId).stream()
				.filter(entry -> Objects.equals(barSpan, entry.barSpan()))
				.anyMatch(entry -> Objects.equals(isin, entry.scrip().getIsin()));
	}
	
	public Set<WatchlistEntry> findByWatchlistId(@NonNull final Integer watchlistId){
		return findAll().stream()
				.filter(entry -> Objects.equals(watchlistId, entry.watchlistId()))
				.collect(Collectors.toSet());
	}
	
	public void deleteByWatchlistId(@NonNull final Integer watchlistId) {
		findAll().stream()
			.filter(entry -> Objects.equals(watchlistId, entry.watchlistId()))
			.map(WatchlistEntry::id)
			.forEach(super::delete);
	}

}
