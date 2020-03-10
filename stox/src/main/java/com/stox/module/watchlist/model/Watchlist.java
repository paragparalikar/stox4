package com.stox.module.watchlist.model;

import java.util.Comparator;
import java.util.Objects;

import com.stox.module.core.model.intf.HasId;

import lombok.Data;
import lombok.NonNull;

@Data
public class Watchlist implements HasId<Integer>, Comparable<Watchlist>{
	public static final Comparator<Watchlist> COMPARATOR = (one, two) -> one.name.compareToIgnoreCase(two.name);  
	
	@NonNull
	private Integer id;
	
	@NonNull
	private String name;
	
	@Override
	public String toString() {
		return name;
	}

	@Override
	public int compareTo(Watchlist other) {
		return Objects.compare(this, other,COMPARATOR);
	}

}
