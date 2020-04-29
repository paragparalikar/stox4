package com.stox.module.watchlist.model;

import java.util.Comparator;
import java.util.Objects;

import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.model.intf.HasBarSpan;
import com.stox.module.core.model.intf.HasId;
import com.stox.module.core.model.intf.HasScrip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class WatchlistEntry implements HasId<Integer>, HasScrip, HasBarSpan, Comparable<WatchlistEntry>{
	public static final Comparator<WatchlistEntry> COMPARATOR = (one, two) -> one.scrip.getName().compareToIgnoreCase(two.scrip().getName());

	private Integer id;
	
	@NonNull
	private Integer watchlistId;
	
	@NonNull
	private Scrip scrip;
	
	@NonNull
	private BarSpan barSpan;

	@Override
	public int compareTo(WatchlistEntry other) {
		return Objects.compare(this, other, COMPARATOR);
	}
	
	@Override
	public String toString() {
		return scrip.getName();
	}
	
}
