package com.stox.module.core.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Scrip implements Comparable<Scrip>{

	private String isin;
	
	private String code;
	
	private String name;
	
	private Exchange exchange;
	
	@Override
	public int compareTo(Scrip scrip) {
		return Objects.compare(name, scrip.name, (one, two) -> one.compareToIgnoreCase(two));
	}
	
	public String toString(){
		return name;
	}
	
}
