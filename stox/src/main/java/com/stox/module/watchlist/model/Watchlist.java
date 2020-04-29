package com.stox.module.watchlist.model;

import java.util.Comparator;
import java.util.Objects;

import com.stox.module.core.model.intf.HasId;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Watchlist implements HasId<Integer>, Comparable<Watchlist>, Cloneable{
	public static final Comparator<Watchlist> COMPARATOR = (one, two) -> one.name.compareToIgnoreCase(two.name);  
	
	private Integer id;
	
	@NonNull
	private String name;
	
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

}
