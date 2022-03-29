package com.stox.watchlist;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WatchlistViewState implements Serializable {
	private static final long serialVersionUID = -3652013909707903017L;

	private String selectedWatchlistName;
	private String selectedWatchlistEntry;
	
}
