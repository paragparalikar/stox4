package com.stox.common.scrip;

import java.io.Serializable;
import java.util.Objects;

import com.stox.common.PropertyContainer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

@Getter
@Builder
@AllArgsConstructor
public class Scrip extends PropertyContainer implements Comparable<Scrip>, Serializable {
	private static final long serialVersionUID = -5267152623133884519L;

	@NonNull private final String isin;
	@NonNull private final String code;
	@NonNull private final String name;
	
	public int compareTo(Scrip scrip) {
		return Objects.compare(name, scrip.name, (one, two) -> one.compareToIgnoreCase(two));
	}
	
	@Override
	public String toString() {
		return name;
	}
}
