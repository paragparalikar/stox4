package com.stox.module.watchlist.repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import com.stox.module.watchlist.model.Watchlist;
import com.stox.util.collection.lazy.LazyMap;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CachedWatchlistRepository implements WatchlistRepository {

	@NonNull private final WatchlistRepository delegate;
	private final Map<String, Watchlist> cache = new LazyMap<>(this::cache);
	
	private Map<String, Watchlist> cache(){
		final Map<String, Watchlist> cache = new HashMap<>();
		final List<Watchlist> watchlists = delegate.findAll();
		watchlists.forEach(watchlist -> cache.put(watchlist.name(), watchlist));
		return cache;
	}

	public List<Watchlist> findAll() {
		final List<Watchlist> watchlists = new ArrayList<>(cache.values());
		watchlists.sort(Watchlist.COMPARATOR);
		return watchlists;
	}

	public Watchlist get(@NonNull final String name) {
		return cache.get(name);
	}

	public boolean exists(@NonNull final String name) {
		return cache.keySet().stream()
				.map(String::trim)
				.map(String::toLowerCase)
				.anyMatch(Predicate.isEqual(name.trim().toLowerCase()));
	}

	public void delete(@NonNull final String name) {
		delegate.delete(name);
		cache.remove(name);
	}

	public Watchlist save(@NonNull final Watchlist watchlist) {
		return cache(delegate.save(watchlist));
	}

	public Watchlist clear(@NonNull final Watchlist watchlist) {
		return cache(delegate.clear(watchlist));
	}

	public Watchlist rename(@NonNull final Watchlist watchlist, @NonNull final String newName) {
		return cache(delegate.rename(watchlist, newName));
	}
	
	private Watchlist cache(final Watchlist watchlist) {
		cache.put(watchlist.name(), watchlist);
		return watchlist;
	}
}
