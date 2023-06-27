package com.stox.watchlist;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Watchlist {

	private String name;
	
	private List<String> entries = new ArrayList<>();
	
	public Watchlist(String name) {
		this.name = name;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
