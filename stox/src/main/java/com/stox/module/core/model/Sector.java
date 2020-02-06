package com.stox.module.core.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sector implements Comparable<Sector> {

	private String id;

	private String code;

	private String name;

	private Exchange exchange;

	@Override
	public int compareTo(Sector sector) {
		return Objects.compare(name, sector.name, (one, two) -> one.compareToIgnoreCase(two));
	}

	public String toString() {
		return name;
	}

}