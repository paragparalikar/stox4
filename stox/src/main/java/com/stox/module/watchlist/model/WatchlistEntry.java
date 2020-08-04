package com.stox.module.watchlist.model;

import java.io.Serializable;
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
public class WatchlistEntry implements HasId<Integer>, HasScrip, HasBarSpan, Comparable<WatchlistEntry>, Serializable{
	private static final long serialVersionUID = 1L;
	public static final Comparator<WatchlistEntry> COMPARATOR_NAME = (one, two) -> one.scrip.name().compareToIgnoreCase(two.scrip().name());
	public static final Comparator<WatchlistEntry> COMPARATOR_INDEX = (one, two) -> Objects.compare(one.index(), two.index(), Comparator.naturalOrder());
	public static final Comparator<WatchlistEntry> COMPARATOR_COMPOSITE = COMPARATOR_INDEX.thenComparing(COMPARATOR_NAME);
	
	private Integer id;
	
	private Integer index;
	
	@NonNull
	private Integer watchlistId;
	
	@NonNull
	private Scrip scrip;
	
	@NonNull
	private BarSpan barSpan;

	@Override
	public int compareTo(WatchlistEntry other) {
		return Objects.compare(this, other, COMPARATOR_COMPOSITE);
	}
	
	@Override
	public String toString() {
		return scrip.name();
	}
	
}
