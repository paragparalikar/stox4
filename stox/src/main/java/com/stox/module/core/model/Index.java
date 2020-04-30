package com.stox.module.core.model;

import java.util.Objects;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(fluent = true)
public class Index implements Comparable<Index> {

	private String id;

	private String code;

	private String name;

	private Exchange exchange;

	@Override
	public int compareTo(Index index) {
		return Objects.compare(name, index.name, (one, two) -> one.compareToIgnoreCase(two));
	}

	public String toString() {
		return name;
	}

}
