package com.stox.watchlist;

import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;

@RequiredArgsConstructor
public class WatchlistService {

	@Delegate
	private final WatchlistRepository watchlistRepository;

}
