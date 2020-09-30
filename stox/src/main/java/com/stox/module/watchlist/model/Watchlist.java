package com.stox.module.watchlist.model;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import com.stox.module.core.model.BarSpan;
import com.stox.module.core.model.Scrip;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
@EqualsAndHashCode(of = "name")
public class Watchlist implements Comparable<Watchlist>, Cloneable{
	public static final Comparator<Watchlist> COMPARATOR = (one, two) -> one.name.compareToIgnoreCase(two.name);  
	
	@NonNull
	private String name;
	
	@NonNull
	private final Map<BarSpan, List<WatchlistEntry>> entries = new EnumMap<>(BarSpan.class);
	
	@Override
	@SneakyThrows
	public Watchlist clone() {
		return (Watchlist) super.clone();
	}
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Watchlist other) {
		return Objects.compare(this, other, COMPARATOR);
	}
	
	public boolean contains(@NonNull final String isin, @NonNull final BarSpan barSpan) {
		return entries.get(barSpan).stream()
				.map(WatchlistEntry::scrip)
				.map(Scrip::isin)
				.anyMatch(Predicate.isEqual(isin));
	}
	
	public void put(@NonNull final WatchlistEntry entry) {
		final List<WatchlistEntry> entries = this.entries.get(entry.barSpan());
		if(null == entry.index()) entry.index(entries.size());
		if(entry.index() == entries.size()) {
			entries.add(entry);
		} else {
			entries.add(entry.index(), entry);
		}
	}
	
	public boolean remove(@NonNull final WatchlistEntry entry) {
		return entries.get(entry.barSpan()).remove(entry);
	}
}
