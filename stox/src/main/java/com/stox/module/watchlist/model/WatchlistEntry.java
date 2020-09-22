package com.stox.module.watchlist.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;
import com.stox.module.core.model.intf.HasBarSpan;
import com.stox.module.core.model.intf.HasScrip;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@Builder
@ToString(of = "scrip")
@Accessors(fluent = true)
public class WatchlistEntry implements HasScrip, HasBarSpan, Comparable<WatchlistEntry>, Serializable{
	private static final long serialVersionUID = 1L;
	public static final Comparator<WatchlistEntry> COMPARATOR_NAME = (one, two) -> one.scrip.name().compareToIgnoreCase(two.scrip().name());
	public static final Comparator<WatchlistEntry> COMPARATOR_INDEX = (one, two) -> Objects.compare(one.index(), two.index(), Comparator.naturalOrder());
	public static final Comparator<WatchlistEntry> COMPARATOR_COMPOSITE = COMPARATOR_INDEX.thenComparing(COMPARATOR_NAME);

	private Integer index;
	@NonNull private final Scrip scrip;
	@NonNull private final BarSpan barSpan;
	@NonNull private final Watchlist watchlist;

	@Override
	public int compareTo(WatchlistEntry other) {
		return Objects.compare(this, other, COMPARATOR_COMPOSITE);
	}
	
}
